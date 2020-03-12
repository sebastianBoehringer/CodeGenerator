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
import edu.horb.dhbw.datacore.uml.commonstructure.Dependency;
import edu.horb.dhbw.datacore.uml.commonstructure.DependencyImpl;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.inputprocessing.restructure.CachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
public final class DependencyRestructurer
        extends CachingRestructurer<Dependency> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public DependencyRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "dependency");
    }

    @Override
    public Dependency restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading dependency from cache",
                     id);
            return processed.get(id);
        }
        Dependency dependency = new DependencyImpl();
        dependency.setId(id);
        processed.put(id, dependency);

        log.info("Processing client for dependency [{}]", id);
        Collection<ModelElement> clients =
                (Collection<ModelElement>) element.getSetAttribute("client");
        List<NamedElement> client = delegateMany(clients, NamedElement.class);
        dependency.setClient(client);

        log.info("Processing supplier for dependency [{}]", id);
        Collection<ModelElement> suppliers =
                (Collection<ModelElement>) element.getSetAttribute("supplier");
        List<NamedElement> supplier =
                delegateMany(suppliers, NamedElement.class);
        dependency.setSupplier(supplier);
        return dependency;
    }
}