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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.util.StringUtils;

/**
 * Specifies an integer value.
 * See subclauses 8.2 and 8.6.9 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public final class LiteralInteger extends LiteralSpecificationImpl<Integer> {
    /**
     * The specified value.
     */
    private final Integer value;

    /**
     * Default constructor.
     * This sets {@link #value} to {@code 0}.
     */
    public LiteralInteger() {

        value = 0;
    }

    /**
     * Creates a new LiteralInteger from the given String representation.
     * If the string is empty, i. e. {@code null} or {@code ""}, the default
     * value is {@code 0}.
     *
     * @param representation The string representation of an integer
     * @return A literalInteger with its value defined by the given
     * representation
     */
    public static LiteralInteger valueOf(final String representation) {

        if (StringUtils.isEmpty(representation)) {
            return new LiteralInteger();
        }
        int temp;
        try {
            temp = Integer.parseInt(representation);
        } catch (NumberFormatException e) {
            temp = 0;
        }
        return new LiteralInteger(temp);
    }
}
