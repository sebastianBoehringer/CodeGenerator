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

package edu.horb.dhbw.inputprocessing.prevalidate.structuredclassifiers;

import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.enums.AggregationKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.EncapsulatedClassifier;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Port;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class PortValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof Port;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        Port port = (Port) base;
        if (!AggregationKind.COMPOSITE.equals(port.getAggregation())) {
            return new Pair<>(Boolean.FALSE,
                              String.format("Port [%s] is not composite",
                                            port.getId()));
        }
        if (port.getType() instanceof Interface
            && port.getDefaultValue() != null) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Port [%s] is typed by an interface but has a "
                    + "defaultValue", port.getId()));
        }
        if (!(port.getOwner() instanceof EncapsulatedClassifier)) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Port [%s] is not owned by an EncapsulatedClassifier",
                    port.getId()));
        }
        return VALID;
    }
}
