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
import edu.horb.dhbw.datacore.uml.classification.Substitution;
import edu.horb.dhbw.datacore.uml.values.OpaqueExpression;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
public final class SubstitutionRestructurer
        extends AbstractCachingRestructurer<Substitution> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public SubstitutionRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "substitution");
    }

    @Override
    public Substitution restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Substitution [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading Substitution from cache",
                     id);
            return processed.get(id);
        }
        Substitution substitution = new Substitution();
        substitution.setId(id);
        processed.putIfAbsent(id, substitution);

        log.debug("Processing umltype for Substitution [{}]", id);
        substitution.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing mapping for Substitution [{}]", id);
        ModelElement mapping = element.getRefAttribute("mapping");
        substitution.setMapping(
                delegateRestructuring(mapping, OpaqueExpression.class));

        log.debug("Processing client for Substitution [{}]", id);
        Collection<ModelElement> clients =
                (Collection<ModelElement>) element.getSetAttribute("client");
        List<Classifier> client = delegateMany(clients, Classifier.class);
        if (client.size() == 1) {
            substitution.setSubstitutingClassifier(client.get(0));
        } else {
            log.error("Substitution [{}] has [{}] as substituting classifier/"
                      + " client instead of 1", id, client.size());
        }
        log.debug("Processing supplier for Substitution [{}]", id);
        Collection<ModelElement> suppliers =
                (Collection<ModelElement>) element.getSetAttribute("supplier");
        List<Classifier> supplier = delegateMany(suppliers, Classifier.class);
        if (supplier.size() == 1) {
            substitution.setSubstitutingClassifier(supplier.get(0));
        } else {
            log.error("Substitution [{}] has [{}] as contract/"
                      + " supplier instead of 1", id, supplier.size());
        }
        log.info("Completed restructuring of Substitution [{}]", id);
        return substitution;
    }
}
