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

package edu.horb.dhbw.inputprocessing.prevalidate.values;

import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.enums.ParameterDirectionKind;
import edu.horb.dhbw.datacore.uml.values.OpaqueExpression;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import lombok.EqualsAndHashCode;
import org.thymeleaf.util.ListUtils;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public final class OpaqueExpressionValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof OpaqueExpression;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        OpaqueExpression expression = (OpaqueExpression) base;
        if (!(ListUtils.isEmpty(expression.getLanguage()))) {
            if (expression.getBody().size() != expression.getLanguage()
                    .size()) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "OpaqueExpression [%s] had languages set but "
                                + "not the same amount as its body"
                                + " strings", expression.getId()));
            }
        }
        if (expression.getBehavior() != null) {
            Behavior behavior = expression.getBehavior();
            List<Parameter> validParams = behavior.getOwnedParameter().stream()
                    .filter(p -> (
                            p.getDirection().equals(ParameterDirectionKind.IN)
                                    && !p.getIsStream()) || p.getDirection()
                            .equals(ParameterDirectionKind.RETURN))
                    .collect(Collectors.toList());
            if (validParams.size() != behavior.getOwnedParameter().size()) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "OpaqueExpression [%s]'s behavior had parameters that "
                                + "were not of direction return or in",
                        expression.getId()));
            }
            List<Parameter> returnParam = validParams.stream()
                    .filter(p -> p.getDirection()
                            .equals(ParameterDirectionKind.RETURN))
                    .collect(Collectors.toList());
            if (returnParam.size() != 1) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "OpaqueExpression [%s]'s behavior did not have "
                                + "exactly one return parameter",
                        expression.getId()));
            }
        }
        return IPreValidator.VALID;
    }
}
