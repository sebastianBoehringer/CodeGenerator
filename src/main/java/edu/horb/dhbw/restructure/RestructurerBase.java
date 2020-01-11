/*
 * Copyright (c) 2019 Sebastian Boehringer.
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

package edu.horb.dhbw.restructure;

import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.CommonElements;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Abstract base implementation of {@link IRestructurer}.
 * Provides utility methods for delegating the restructuring task to other
 * {@link IRestructurer}s.
 *
 * @param <T> Same as the type parameter of {@link IRestructurer}
 */
public abstract class RestructurerBase<T extends CommonElements>
        implements IRestructurer<T> {

    /**
     * The factory that created this restructurer. This is used to access
     * other restructurers.
     */
    @Getter
    @Setter
    private IRestructurerMediator mediator;

    /**
     * The name of the element from the metamodel the {@link IRestructurer}
     * processes.
     */
    private final String umlType;

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
    public RestructurerBase(final IRestructurerMediator iRestructurerMediator,
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
    public @NonNull Collection<T> restructure(final Model model) {

        final MetaModel metaModel = model.getMetaModel();
        Collection<ModelElement> classes =
                model.getElements(metaModel.getType(umlType));
        List<T> processed = classes.size() > 0 ? new ArrayList<>()
                                               : Collections.emptyList();
        for (ModelElement element : classes) {
            processed.add(restructure(element));
        }

        return processed;
    }

    /**
     * Restructures a single element instead of the entire model.
     *
     * @param element The modelElement to restructure
     * @return A restrucutured uml class
     */
    public abstract T restructure(@NonNull ModelElement element);

    /**
     * Checks to see if the restructurer already processed a certain element.
     *
     * @param id The id of an element
     * @return {@code true} if the {@link IRestructurer} already processed the
     * instance, {@code false} if the id is null or was not processed
     * by the {@link IRestructurer}.
     */
    public boolean wasProcessed(final String id) {

        return id != null && getProcessed(id).isPresent();
    }

    /**
     * Cleans the cache if the extenden restructurer uses one.
     */
    public abstract void cleanCache();

    /**
     * Returns an element identified by the given id if the IRestructurer
     * already processed it.
     *
     * @param id The id of an element
     * @return An optional wrapping the element if it was already processed,
     * an empty Optional otherwise
     */
    public abstract Optional<T> getProcessed(@NonNull String id);

    /**
     * Delegates the restructuring task of multiple {@link ModelElement}s to
     * specialized restructorers. This wraps
     * {@link #delegateRestructuring(ModelElement, Class)} with a size check.
     *
     * @param modelElements The {@link ModelElement}s to restructurer.
     * @param tClass        The class that should be restructured to
     * @param <V>           The class to be restructured to. Upper type is
     *                      {@link CommonElements} so that a restructurer may
     *                      exist.
     * @return A collection of the restructured elements
     */
    protected final <V extends CommonElements> @NonNull List<V> delegateMany(
            @NonNull final Collection<ModelElement> modelElements,
            @NonNull final Class<V> tClass) {

        if (modelElements.isEmpty()) {
            return Collections.emptyList();
        } else {
            return modelElements.stream()
                    .map(e -> delegateRestructuring(e, tClass))
                    .collect(Collectors.toList());
        }
    }

    /**
     * @param element The element to restructure.
     * @param vClass  The class to restructure to
     * @param <V>     The type of the class to restructure to. Upper bound is
     *                {@link CommonElements} so that a restructurer can exist.
     * @return The restructured element
     */
    protected final <V extends CommonElements> V delegateRestructuring(final ModelElement element,
                                                                       @NonNull
                                                                       final Class<V> vClass) {

        if (element == null) {
            System.out.println(
                    String.format("Element was null, should become a %s",
                                  vClass.getSimpleName()));
            return null;
        }
        IRestructurer<V> restructurer = mediator.getIRestructurer(vClass);
        System.out.println(String.format("Element is %s", element.getName()));
        return restructurer.restructure(element);
    }
}
