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

package edu.horb.dhbw.inputprocessing.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

@Slf4j
public final class GeneralizationRestructurer
        extends AbstractCachingRestructurer<Generalization> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public GeneralizationRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "generalization");
    }

    @Override
    public Generalization restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Generalization [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading Generalization from "
                     + "cache", id);
            return processed.get(id);
        }
        Generalization generalization = new Generalization();
        generalization.setId(id);
        processed.putIfAbsent(id, generalization);

        log.debug("Processing umltype for Generalization [{}]", id);
        generalization.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing general for Generalization [{}]", id);
        ModelElement general = element.getRefAttribute("general");
        generalization
                .setGeneral(delegateRestructuring(general, Classifier.class));

        log.debug("Processing specific for Generalization [{}]", id);
        ModelElement specific = element.getRefAttribute("specific");
        generalization
                .setSpecific(delegateRestructuring(specific, Classifier.class));

        log.debug("Processing substitutable for Generalization [{}]", id);
        String substitutable = element.getPlainAttribute("substitutable");
        generalization.setIsSubstitutable(
                StringUtils.isEmpty(substitutable) ? Boolean.TRUE : Boolean
                        .valueOf(substitutable));
        log.info("Completed restructuring of Generalization [{}]", id);
        return generalization;
    }
}
