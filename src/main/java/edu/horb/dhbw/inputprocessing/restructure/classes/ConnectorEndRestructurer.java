/*
 * Copyright (c) 2020 Sebastian Boehringer.
 *  This file is part of the CodeGenerator.
 *
 *  CodeGenerator is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * CodeGenerator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License along with CodeGenerator.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.horb.dhbw.inputprocessing.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectableElement;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectorEnd;
import edu.horb.dhbw.inputprocessing.restructure.CachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

@Slf4j
public final class ConnectorEndRestructurer
        extends CachingRestructurer<ConnectorEnd> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ConnectorEndRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "connectorend");
    }

    @Override
    public ConnectorEnd restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading connectorend from cache",
                     id);
            return processed.get(id);
        }
        ConnectorEnd end = new ConnectorEnd();
        end.setId(id);
        processed.put(id, end);

        log.info("Processing ordered for connectorend [{}]", id);
        String ordered = element.getPlainAttribute("ordered");
        end.setIsOrdered(Boolean.valueOf(ordered));

        log.info("Processing unique for connectorend [{}]", id);
        String unique = element.getPlainAttribute("unique");
        //Default value for isUnique is true, see uml specification subclause
        // 7.8.8
        boolean isUnique;
        if (unique == null || "".equals(unique)) {
            isUnique = true;
        } else {
            isUnique = Boolean.parseBoolean(unique);
        }
        end.setIsUnique(isUnique);

        log.info("Processing lower for connectorend [{}]", id);
        String lower = element.getPlainAttribute("lower");
        if (!StringUtils.isEmpty(lower)) {
            end.setLower(Integer.parseInt(lower));
        }
        log.info("Processing upper for connectorend [{}]", id);
        String upper = element.getPlainAttribute("upper");
        if (!StringUtils.isEmpty(upper)) {
            end.setUpper(new UnlimitedNatural(upper));
        }
        log.info("Processing role for connectorend [{}]", id);
        ModelElement role = element.getRefAttribute("role");
        end.setRole(delegateRestructuring(role, ConnectableElement.class));

        return end;
    }

}
