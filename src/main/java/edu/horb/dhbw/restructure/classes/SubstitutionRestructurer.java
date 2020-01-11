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
import edu.horb.dhbw.datacore.uml.classification.Substitution;
import edu.horb.dhbw.datacore.uml.values.OpaqueExpression;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public final class SubstitutionRestructurer
        extends RestructurerBase<Substitution> {
    /**
     * A map holding all the {@link Substitution}s that have already been
     * processed. This maps from their xmi id to the actual instance. The map
     * is not synchronized, thus the class is most likely not threadsafe.
     */
    private static final Map<String, Substitution> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public SubstitutionRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "substitution");
    }

    @Override
    public Substitution restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] in cache, loading substitution from cache",
                     id);
            return ALREADY_PROCESSED.get(id);
        }
        Substitution substitution = new Substitution();
        substitution.setId(id);
        ALREADY_PROCESSED.put(id, substitution);

        log.info("Processing mapping for substitution [{}]", id);
        ModelElement mapping = element.getRefAttribute("mapping");
        substitution.setMapping(
                delegateRestructuring(mapping, OpaqueExpression.class));

        //TODO client
        log.info("Processing client for substitution [{}]", id);
        Collection<ModelElement> clients =
                (Collection<ModelElement>) element.getSetAttribute("client");
        List<Classifier> client = delegateMany(clients, Classifier.class);
        if (client.size() == 1) {
            substitution.setSubstitutingClassifier(client.get(0));
        } else {
            log.error("Substitution [{}] has [{}] as substituting classifier/"
                              + " client instead of 1", id, client.size());
        }
        log.info("Processing supplier for substitution [{}]", id);
        Collection<ModelElement> suppliers =
                (Collection<ModelElement>) element.getSetAttribute("supplier");
        List<Classifier> supplier = delegateMany(suppliers, Classifier.class);
        if (supplier.size() == 1) {
            substitution.setSubstitutingClassifier(supplier.get(0));
        } else {
            log.error("Substitution [{}] has [{}] as contract/"
                              + " supplier instead of 1", id, supplier.size());
        }

        return substitution;
    }

    @Override
    public Optional<Substitution> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
    @Override
    public void cleanCache() {
        ALREADY_PROCESSED.clear();
    }
}
