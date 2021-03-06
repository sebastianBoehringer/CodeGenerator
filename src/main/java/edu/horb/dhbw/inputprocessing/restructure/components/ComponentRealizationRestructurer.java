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

package edu.horb.dhbw.inputprocessing.restructure.components;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Component;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ComponentRealization;
import edu.horb.dhbw.datacore.uml.values.OpaqueExpression;
import edu.horb.dhbw.inputprocessing.restructure.AbstractRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
public final class ComponentRealizationRestructurer
        extends AbstractRestructurer<ComponentRealization> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ComponentRealizationRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "componentrealization");
    }

    @Override
    public ComponentRealization restructure(
            @NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of ComponentRealization [{}]", id);
        ComponentRealization realization = new ComponentRealization();
        realization.setId(id);

        log.debug("Processing umltype for ComponentRealization [{}]", id);
        realization.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing mapping for ComponentRealization [{}]", id);
        ModelElement mapping = element.getRefAttribute("mapping");
        realization.setMapping(
                delegateRestructuring(mapping, OpaqueExpression.class));

        log.debug("Processing client for ComponentRealization [{}]", id);
        Collection<ModelElement> clients =
                (Collection<ModelElement>) element.getSetAttribute("client");
        List<Classifier> client = delegateMany(clients, Classifier.class);
        realization.setRealizingClassifier(client);

        log.debug("Processing component for ComponentRealization [{}]", id);
        ModelElement component = element.getRefAttribute("component");
        realization.setAbstraction(
                delegateRestructuring(component, Component.class));
        log.info("Completed restructuring of ComponentRealization [{}]", id);
        return realization;
    }
}
