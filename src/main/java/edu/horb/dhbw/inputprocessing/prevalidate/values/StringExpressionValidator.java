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
import edu.horb.dhbw.datacore.uml.values.LiteralString;
import edu.horb.dhbw.datacore.uml.values.StringExpression;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import org.thymeleaf.util.ListUtils;

import java.util.Optional;

public final class StringExpressionValidator implements IPreValidator {

    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof StringExpression;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        StringExpression expression = (StringExpression) base;
        if (!ListUtils.isEmpty(expression.getSubExpression())) {
            if (!ListUtils.isEmpty(expression.getOperand())) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "StringExpression [%s] has both subexpressions and "
                                + "operands", expression.getId()));
            }
        } else {
            if (ListUtils.isEmpty(expression.getOperand())) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "StringExpression [%s] neither has subexpressions nor "
                                + "operands", expression.getId()));
            }
        }

        Optional<?> operand = expression.getOperand().stream()
                .filter(o -> !(o instanceof LiteralString)).findFirst();
        if (operand.isPresent()) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "StringExpression [%s] had an operand that was not a"
                            + " LiteralString", expression.getId()));
        }
        return IPreValidator.VALID;
    }
}
