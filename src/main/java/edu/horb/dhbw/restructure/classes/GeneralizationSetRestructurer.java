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
import edu.horb.dhbw.datacore.uml.classification.GeneralizationSet;
import edu.horb.dhbw.restructure.CachingRestructurer;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public final class GeneralizationSetRestructurer
        extends CachingRestructurer<GeneralizationSet> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public GeneralizationSetRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "generalizationset");
    }

    @Override
    public GeneralizationSet restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading generalizationset from "
                             + "cache", id);
            return processed.get(id);
        }
        GeneralizationSet generalizationSet = new GeneralizationSet();
        generalizationSet.setId(id);
        processed.put(id, generalizationSet);

        log.info("Processing name for generalizationset [{}]", id);
        String name = element.getPlainAttribute("name");
        generalizationSet.setName(name);

        log.info("Processing disjoint for generalizationset [{}]", id);
        String disjoint = element.getPlainAttribute("disjoint");
        generalizationSet.setIsDisjoint(Boolean.valueOf(disjoint));

        log.info("Processing covering for generalizationset [{}]", id);
        String covering = element.getPlainAttribute("covering");
        generalizationSet.setIsCovering(Boolean.valueOf(covering));

        log.info("Processing generalizations for generalizationset [{}]", id);
        Collection<ModelElement> generalizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("generalizations");
        generalizationSet.setGeneralization(
                delegateMany(generalizations, Generalization.class));

        log.info("Processing powertype for generalizationset [{}]", id);
        ModelElement powertype = element.getRefAttribute("powertype");
        generalizationSet.setPowertype(
                delegateRestructuring(powertype, Classifier.class));

        return generalizationSet;
    }
}
