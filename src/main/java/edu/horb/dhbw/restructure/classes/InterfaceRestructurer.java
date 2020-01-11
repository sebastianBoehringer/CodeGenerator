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

package edu.horb.dhbw.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public final class InterfaceRestructurer extends RestructurerBase<Interface> {
    /**
     * A map holding all the {@link Interface}s that have already been
     * processed. This maps from their xmi id to the actual instance. The map
     * is not synchronized, thus the class is most likely not threadsafe.
     */
    private static final Map<String, Interface> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public InterfaceRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "interface");
    }

    @Override
    public Interface restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] in cache, loading interface from cache",
                     id);
            return ALREADY_PROCESSED.get(id);
        }
        Interface anInterface = new Interface();
        anInterface.setId(id);
        ALREADY_PROCESSED.put(id, anInterface);

        log.info("Processing name for interface [{}]", id);
        String name = element.getPlainAttribute("name");
        anInterface.setName(name);

        log.info("Processing visibility for interface [{}]", id);
        String visibility = element.getPlainAttribute("visibility");
        anInterface.setVisibility(
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility));

        log.info("Processing ownedattributes for interface [{}]", id);
        Collection<ModelElement> attributes = (Collection<ModelElement>) element
                .getSetAttribute("ownedattributes");
        anInterface.setOwnedAttribute(delegateMany(attributes, Property.class));

        log.info("Processing ownedoperations for interface [{}]", id);
        Collection<ModelElement> operations = (Collection<ModelElement>) element
                .getSetAttribute("ownedoperations");
        anInterface
                .setOwnedOperation(delegateMany(operations, Operation.class));

        log.info("Processing nestedclassifiers for interface [{}]", id);
        Collection<ModelElement> nested = (Collection<ModelElement>) element
                .getSetAttribute("nestedclassifiers");
        List<Classifier> classifiers = delegateMany(nested, Classifier.class);
        anInterface.setNestedClassifier(classifiers);

        log.info("Processing generalizations for interface [{}]", id);
        Collection<ModelElement> generalizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("generalizations");
        anInterface.setGeneralization(
                delegateMany(generalizations, Generalization.class));

        return anInterface;
    }

    @Override
    public Optional<Interface> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }

    @Override
    public void cleanCache() {

        ALREADY_PROCESSED.clear();
    }
}
