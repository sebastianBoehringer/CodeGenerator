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

package edu.horb.dhbw.datacore.model;

public enum StatementKind {
    /**
     * An opaque statement. Its meaning cannot be derived by the codegenerator.
     */
    OPAQUE,
    /**
     * Creates and assigns a variable.
     */
    CREATEANDASSIGN,
    /**
     * Creates a variable.
     */
    CREATION,
    /**
     * A call to a function, possibly chained.
     */
    FUNCTIONCALL,
    /**
     * A choice between multiple different statements by evaluating a condition.
     */
    CHOICE,
    /**
     * A loop statement repating a series of statements while a condition holds.
     */
    LOOP,
    /**
     * Multiple series of statements tht are executed at the same time.
     */
    PARALLEL
}
