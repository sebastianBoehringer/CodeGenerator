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

/**
 * Determines the way a parameter is used in an
 * {@link edu.horb.dhbw.datacore.uml.classification.Operation}.
 * <br/>
 * See subclauses 9.4 and 9.9.14 of the UML specification for more details.
 */
public enum ParameterDirectionKind {
    /**
     * The parameter is used just as input.
     */
    IN,
    /**
     * Indicates that the values are passed into the operation but they might
     * be changed during execution.
     */
    INOUT,
    /**
     * These kinds of paramters are output to the caller.
     */
    OUT,
    /**
     * The parameter holds the computation result of the operation.
     */
    RETURN
}
