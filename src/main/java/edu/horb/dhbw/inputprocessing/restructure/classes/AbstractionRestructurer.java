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
import edu.horb.dhbw.datacore.uml.commonstructure.Abstraction;
import edu.horb.dhbw.datacore.uml.commonstructure.AbstractionImpl;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.values.OpaqueExpression;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
public final class AbstractionRestructurer
        extends AbstractCachingRestructurer<Abstraction> {


    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public AbstractionRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "abstraction");
    }

    @Override
    public Abstraction restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Abstraction [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading Abstraction from cache",
                     id);
            return processed.get(id);
        }
        Abstraction abstraction = new AbstractionImpl();
        abstraction.setId(id);
        processed.putIfAbsent(id, abstraction);

        log.debug("Processing umltype for Abstraction [{}]", id);
        abstraction.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing mapping for Abstraction [{}]", id);
        ModelElement mapping = element.getRefAttribute("mapping");
        abstraction.setMapping(
                delegateRestructuring(mapping, OpaqueExpression.class));

        log.debug("Processing client for Abstraction [{}]", id);
        Collection<ModelElement> clients =
                (Collection<ModelElement>) element.getSetAttribute("client");
        List<NamedElement> client = delegateMany(clients, NamedElement.class);
        abstraction.setClient(client);

        log.debug("Processing supplier for Abstraction [{}]", id);
        Collection<ModelElement> suppliers =
                (Collection<ModelElement>) element.getSetAttribute("supplier");
        List<NamedElement> supplier =
                delegateMany(suppliers, NamedElement.class);
        abstraction.setSupplier(supplier);
        log.info("Completed restructuring of Abstraction [{}]", id);
        return abstraction;
    }
}
