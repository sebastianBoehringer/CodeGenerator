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

/**
 * Denotes that the value of the ValueSpecification does not need to be
 * computed, i. e. the value is constant.
 * See subclauses 8.2 and 8.6.12 of the UML specification for more details.
 *
 * @param <T> The type of the constant value that this specification returns.
 */
public interface LiteralSpecification<T> extends ValueSpecification {

    /**
     * Easy access to the {@link Object#toString()} of the wrapped value.
     *
     * @return A string representation of {@link #getValue()}.
     */
    default String stringify() {

        return getValue().toString();
    }

    T getValue();

}
