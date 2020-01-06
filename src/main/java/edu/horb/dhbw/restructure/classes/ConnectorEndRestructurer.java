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
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectorEnd;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ConnectorEndRestructurer
        extends RestructurerBase<ConnectorEnd> {

    /**
     * A map holding all the {@link ConnectorEnd}s that have already been
     * processed. This maps from their xmi id to the actual instance.
     * The map is not synchronized, thus the class is most likely not
     * threadsafe.
     */
    private static final Map<String, ConnectorEnd> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ConnectorEndRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "connectorend");
    }

    @Override
    public ConnectorEnd restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            return ALREADY_PROCESSED.get(id);
        }
        ConnectorEnd end = new ConnectorEnd();
        end.setId(id);

        String ordered = element.getPlainAttribute("ordered");
        end.setIsOrdered(Boolean.valueOf(ordered));

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

        String lower = element.getPlainAttribute("lower");
        if (lower != null && !("".equals(lower))) {
            end.setLower(Integer.parseInt(lower));
        }
        String upper = element.getPlainAttribute("upper");
        if (upper != null && !("".equals(upper))) {
            end.setUpper(new UnlimitedNatural(upper));
        }
        ModelElement role = element.getRefAttribute("role");
        //TODO process role, type classifier

        ALREADY_PROCESSED.put(id, end);
        return end;
    }

    @Override
    public Optional<ConnectorEnd> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
}
