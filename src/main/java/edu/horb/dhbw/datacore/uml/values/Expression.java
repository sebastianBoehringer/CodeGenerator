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
 * Specifies a value by applying the operation represented by the
 * {@link #symbol} to each of the {@link #operand}s.
 * <br/>
 * See subclauses 8.3 and 8.6.5 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expression extends ValueSpecification {
    /**
     * Represents the operation to apply to the {@link #operand}s.
     */
    private String symbol;
    /**
     * The operands this expression uses to generate a value.
     */
    private List<ValueSpecification> operand = new ArrayList<>();

    /**
     * Adds a new operand to {@link #operand}.
     *
     * @param newOperand The operand to add.
     */
    public void addOperand(final ValueSpecification newOperand) {

        operand.add(newOperand);
    }
}
