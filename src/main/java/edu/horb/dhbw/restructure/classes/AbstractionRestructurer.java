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
import edu.horb.dhbw.datacore.uml.commonstructure.Abstraction;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
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
public class AbstractionRestructurer extends RestructurerBase<Abstraction> {
    /**
     * A map holding all the {@link Abstraction}s that have already been
     * processed. This maps from their xmi id to the actual instance. The map
     * is not synchronized, thus the class is most likely not threadsafe.
     */
    private static final Map<String, Abstraction> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public AbstractionRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "abstraction");
    }

    @Override
    public Abstraction restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] in cache, loading abstraction from cache",
                     id);
            return ALREADY_PROCESSED.get(id);
        }
        Abstraction abstraction = new Abstraction();
        abstraction.setId(id);
        ALREADY_PROCESSED.put(id, abstraction);

        log.info("Processing mapping for abstraction [{}]", id);
        ModelElement mapping = element.getRefAttribute("mapping");
        abstraction.setMapping(
                delegateRestructuring(mapping, OpaqueExpression.class));

        log.info("Processing client for abstraction [{}]", id);
        Collection<ModelElement> clients =
                (Collection<ModelElement>) element.getSetAttribute("client");
        List<NamedElement> client = delegateMany(clients, NamedElement.class);
        abstraction.setClient(client);

        log.info("Processing supplier for abstraction [{}]", id);
        Collection<ModelElement> suppliers =
                (Collection<ModelElement>) element.getSetAttribute("supplier");
        List<NamedElement> supplier =
                delegateMany(suppliers, NamedElement.class);
        abstraction.setSupplier(supplier);
        return abstraction;
    }

    @Override
    public Optional<Abstraction> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }

    @Override
    public void cleanCache() {

        ALREADY_PROCESSED.clear();
    }
}