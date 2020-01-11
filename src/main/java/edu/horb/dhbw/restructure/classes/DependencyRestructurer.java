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
import edu.horb.dhbw.datacore.uml.commonstructure.Dependency;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
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
public final class DependencyRestructurer extends RestructurerBase<Dependency> {
    /**
     * A map holding all the {@link Dependency}s that have already been
     * processed. This maps from their xmi id to the actual instance. The map
     * is not synchronized, thus the class is most likely not threadsafe.
     */
    private static final Map<String, Dependency> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public DependencyRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "dependency");
    }

    @Override
    public Dependency restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] in cache, loading dependency from cache",
                     id);
            return ALREADY_PROCESSED.get(id);
        }
        Dependency dependency = new Dependency();
        dependency.setId(id);
        ALREADY_PROCESSED.put(id, dependency);

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

    @Override
    public Optional<Dependency> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }

    @Override
    public void cleanCache() {

        ALREADY_PROCESSED.clear();
    }
}
