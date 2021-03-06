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
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Association;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Connector;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectorEnd;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public final class ConnectorRestructurer
        extends AbstractCachingRestructurer<Connector> {


    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ConnectorRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "connector");
    }

    @Override
    public Connector restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Connector [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading Connector from cache",
                     id);
            return processed.get(id);
        }
        Connector connector = new Connector();
        connector.setId(id);
        processed.putIfAbsent(id, connector);

        log.debug("Processing umltype for Connector [{}]", id);
        connector.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing type for Connector [{}]", id);
        ModelElement type = element.getRefAttribute("type");
        connector.setType(delegateRestructuring(type, Association.class));

        log.debug("Processing ends for Connector [{}]", id);
        Collection<ModelElement> ends =
                (Collection<ModelElement>) element.getSetAttribute("ends");
        connector.setEnd(delegateMany(ends, ConnectorEnd.class));
        log.info("Completed restructuring of Connector [{}]", id);
        return connector;
    }
}
