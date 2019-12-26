/*
 * Copyright (c) 2019 Sebastian Boehringer.
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

package edu.horb.dhbw.datacore.uml.values;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Specifies a String value that is computed by concatenating all of the
 * values of its subexpressions.
 * See subclauses 8.3 and 8.6.17 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class StringExpression extends Expression {
    /**
     * The expression that owns this expression.
     */
    private StringExpression owningExpression;
    /**
     * The subexpressions of this expression.
     */
    private List<StringExpression> subExpression = new ArrayList<>();

    /**
     * Adds a new expression to {@link #subExpression}.
     *
     * @param expression The expression to add
     */
    public void addSubExpression(final StringExpression expression) {

        subExpression.add(expression);
    }
}
