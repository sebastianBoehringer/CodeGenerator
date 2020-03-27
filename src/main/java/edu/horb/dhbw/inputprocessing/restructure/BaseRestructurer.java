/*
 * Copyright (c) 2020 Sebastian Boehringer.
 *  This file is part of the CodeGenerator.
 *
 *  CodeGenerator is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 *  option) any later version.
 * CodeGenerator is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public
 * License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 * along with CodeGenerator.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.horb.dhbw.inputprocessing.restructure;

import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.exception.NotYetImplementedException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Abstract base implementation of {@link IRestructurer}.
 * Provides utility methods for delegating the restructuring task to other
 * {@link IRestructurer}s.
 *
 * @param <T> Same as the type parameter of {@link IRestructurer}
 */
public abstract class BaseRestructurer<T extends XMIElement>
        implements IRestructurer<T> {

    /**
     * The name of the element from the metamodel the {@link IRestructurer}
     * processes.
     */
    private final String umlType;
    /**
     * The factory that created this restructurer. This is used to access
     * other restructurers.
     */
    @Getter
    @Setter
    private IRestructurerMediator mediator;

    /**
     * Constructor.
     * Initializes the reference to an {@link IRestructurerMediator} that
     * provides other {@link IRestructurer}s so that they may process the
     * other uml classes a class can own.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     * @param type                  The name of the metamodelelement this class
     *                              processes
     */
    public BaseRestructurer(final IRestructurerMediator iRestructurerMediator,
                            @NonNull final String type) {

        mediator = iRestructurerMediator;
        umlType = type;
    }

    /**
     * Transforms (parts of) a model into a collection corresponding uml class
     * as found in the packages under edu.horb.dhbw.datacore.uml.*. Note that
     * this treats the model as an immutable object and does not change any
     * of its contents. So one can apply other IRestructurers to the same model.
     * The return value of this method will never be null.
     *
     * @param model The model to restructure
     * @return A collection with the restructured uml classes.
     */
    @Override
    public @NonNull List<T> restructure(final Model model) {

        final MetaModel metaModel = model.getMetaModel();
        List<ModelElement> classes =
                model.getElements(metaModel.getType(umlType));
        if (ListUtils.isEmpty(classes)) {
            return Collections.emptyList();
        }
        List<T> processed = new ArrayList<>(classes.size());
        for (ModelElement element : classes) {
            processed.add(restructure(element));
        }

        return processed;
    }

    @Override
    public <S extends T> S restructure(@NonNull S base,
                                       @NonNull ModelElement element) {

        throw new NotYetImplementedException(this.getClass(),
                                             "restructure(base, element)");
    }

    @Override
    public boolean canRestructure(final @NonNull ModelElement element) {

        String type = element.getPlainAttribute("umltype");
        return umlType.equals(type);
    }

    /**
     * Delegates the restructuring task of multiple {@link ModelElement}s to
     * specialized restructorers. This wraps
     * {@link #delegateRestructuring(ModelElement, Class)} with a size check.
     *
     * @param modelElements The {@link ModelElement}s to restructurer.
     * @param vClass        The class that should be restructured to
     * @param <V>           The class to be restructured to. Upper type is
     *                      {@link XMIElement} so that a restructurer may
     *                      exist.
     * @return A collection of the restructured elements. The collection will
     * not contain {@code null} elements
     */
    protected final <V extends XMIElement> @NonNull List<V> delegateMany(
            @NonNull final Collection<ModelElement> modelElements,
            @NonNull final Class<V> vClass) {

        if (modelElements.isEmpty()) {
            return Collections.emptyList();
        } else {
            return modelElements.stream()
                    .map(e -> delegateRestructuring(e, vClass))
                    .filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    /**
     * @param element The element to restructure.
     * @param vClass  The class to restructure to
     * @param <V>     The type of the class to restructure to. Upper bound is
     *                {@link XMIElement} so that a restructurer can exist.
     * @return The restructured element
     */
    protected final <V extends XMIElement> V delegateRestructuring(final ModelElement element, final Class<V> vClass) {

        if (element == null || vClass == null) {
            return null;
        }
        IRestructurer<V> restructurer = mediator.getIRestructurer(vClass);
        return restructurer.restructure(element);
    }
}
