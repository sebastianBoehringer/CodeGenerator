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

package edu.horb.dhbw.datacore.uml.enums;

import lombok.NonNull;

/**
 * Indicates the effect a behavior has on the values of its parameters.
 * See subclauses 9.4 and 9.9.15 of the UML specification for more details.
 */
public enum ParameterEffectKind {
    /**
     * The behavior creates a value.
     */
    CREATE,
    /**
     * The behavior only reads the value of a parameter and its associated
     * links.
     */
    READ,
    /**
     * The behavior may change attributes of the parameter and its associated
     * links.
     */
    UPDATE,
    /**
     * The behavior deletes the object. It does not exist after execution
     * anymore.
     */
    DELETE,
    /**
     * The effect of the behavior on the parameter is not known.
     * This special value indicates that the modelling tool did not serialize
     * the effect attribute of the uml class Parameter. That attribute does
     * not have a default value, which is expressed by this enum value.
     */
    UNDEFINED;

    /**
     * Case insensitive wrapper around {@link #valueOf(String)}.
     *
     * @param string The string identifying an enum constant
     * @return The enum constant identified by the string
     */
    public static ParameterEffectKind from(@NonNull final String string) {

        return ParameterEffectKind.valueOf(string.toUpperCase());
    }
}
