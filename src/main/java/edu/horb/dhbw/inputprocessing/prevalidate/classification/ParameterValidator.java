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

package edu.horb.dhbw.inputprocessing.prevalidate.classification;

import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.enums.ParameterDirectionKind;
import edu.horb.dhbw.datacore.uml.enums.ParameterEffectKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.DataType;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class ParameterValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof Parameter;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        Parameter param = (Parameter) base;
        if (ParameterEffectKind.DELETE.equals(param.getEffect()) && !isInward(
                param.getDirection())) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Parameter [%s] has effect delete but its  "
                    + "direction is not in or inout", param.getId()));
        }
        if (ParameterEffectKind.CREATE.equals(param.getEffect()) && !isOutward(
                param.getDirection())) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Parameter [%s] has effect create but is of type in",
                    param.getId()));
        }
        if (param.getDirection().equals(ParameterDirectionKind.IN) && param
                .getIsException()) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Parameter [%s] is exception but " + "direction is in",
                    param.getId()));
        }

        if (param.getIsStream() && param.getIsException()) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Parameter [%s] is exception and stream at the same "
                    + "time", param.getId()));
        }
        if (!ParameterEffectKind.UNDEFINED.equals(param.getEffect()) && param
                .getType() instanceof DataType) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Parameter [%s] is typed by a dataType but has an "
                    + "effect", param.getId()));
        }
        return VALID;
    }

    private boolean isInward(final ParameterDirectionKind kind) {

        return kind.equals(ParameterDirectionKind.IN) || kind
                .equals(ParameterDirectionKind.INOUT);
    }

    private boolean isOutward(final ParameterDirectionKind kind) {

        return !kind.equals(ParameterDirectionKind.IN);
    }
}
