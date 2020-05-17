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

package edu.horb.dhbw.inputprocessing.prevalidate.commonbehavior;

import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.commonbehavior.FunctionBehavior;
import edu.horb.dhbw.datacore.uml.enums.ParameterDirectionKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.DataType;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public final class FunctionBehaviorValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof FunctionBehavior;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        FunctionBehavior behavior = (FunctionBehavior) base;
        List<Parameter> outputParams = behavior.getOwnedParameter().stream()
                .filter(p -> !ParameterDirectionKind.IN
                        .equals(p.getDirection())).collect(Collectors.toList());
        if (outputParams.size() < 1) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "FunctionBehavior [%s] has less than 1 outgoing"
                    + " parameter", behavior.getId()));
        }
        for (Parameter parameter : behavior.getOwnedParameter()) {
            if (parameter.getType() instanceof DataType) {
                DataType dataType = (DataType) parameter.getType();
                if (!onlyDataTypeAttributes(dataType)) {
                    return new Pair<>(Boolean.FALSE, String.format(
                            "FunctionBehavior [%s] has a parameter with a "
                            + "DataType that has attributes not typed"
                            + " by a DataType. Namely Datatype [%s]",
                            behavior.getId(), dataType.getId()));
                }
            } else {
                return new Pair<>(Boolean.FALSE, String.format(
                        "FunctionBehavior [%s] has a parameter that is not "
                        + "typed by a DataType", behavior.getId()));
            }
        }
        return VALID;
    }

    private boolean onlyDataTypeAttributes(final DataType dataType) {

        boolean flag = false;
        for (Property property : dataType.getOwnedAttribute()) {
            if (!(property.getType() instanceof DataType)) {
                return false;
            } else {
                flag = onlyDataTypeAttributes((DataType) property.getType());
            }
            if (!flag) {
                break;
            }
        }
        return flag;
    }
}
