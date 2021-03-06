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
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Usage;
import edu.horb.dhbw.datacore.uml.commonstructure.UsageImpl;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
public final class UsageRestructurer
        extends AbstractCachingRestructurer<Usage> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public UsageRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "usage");
    }

    @Override
    public Usage restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Usage [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading Usage from cache", id);
            return processed.get(id);
        }
        Usage usage = new UsageImpl();
        usage.setId(id);
        processed.putIfAbsent(id, usage);

        log.debug("Processing umltype for Usage [{}]", id);
        usage.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing client for Usage [{}]", id);
        Collection<ModelElement> clients =
                (Collection<ModelElement>) element.getSetAttribute("client");
        List<NamedElement> client = delegateMany(clients, NamedElement.class);
        usage.setClient(client);

        log.debug("Processing supplier for Usage [{}]", id);
        Collection<ModelElement> suppliers =
                (Collection<ModelElement>) element.getSetAttribute("supplier");
        List<NamedElement> supplier =
                delegateMany(suppliers, NamedElement.class);
        usage.setSupplier(supplier);
        log.info("Completed restructuring of Usage [{}]", id);
        return usage;
    }
}
