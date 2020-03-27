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

package edu.horb.dhbw.inputprocessing.postvalidate;

public enum FirstLetter {
    /**
     * Signifies that the first letter of the name of the
     * {@link edu.horb.dhbw.datacore.model.OOType} to validate must be in
     * uppercase.
     */
    UPPER,
    /**
     * Signifies that the first letter of the name of the
     * {@link edu.horb.dhbw.datacore.model.OOType} to validate can be in
     * lowercase or uppercase.
     */
    EITHER,
    /**
     * Signifies that the first letter of the name of the
     * {@link edu.horb.dhbw.datacore.model.OOType} to validate must be in
     * lowercase.
     */
    LOWER;

    /**
     * Case insensitive wrapper around {@link #valueOf(String)}.
     *
     * @param literal The string representing a literal of this enumeration
     * @return The enumerationliteral represented to the string
     */
    public static FirstLetter from(final String literal) {

        return valueOf(literal.toUpperCase());
    }
}
