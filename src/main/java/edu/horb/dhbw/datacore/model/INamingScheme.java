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

/**
 * A strategy that provides details of how naming for a {@link Language} works.
 */
public interface INamingScheme {

    /**
     * Provides the file name for a given oo class.
     * This does NOT include the extension. That can be returned by
     * {@link Language#getExtension()}.
     *
     * @return The name of the file.
     */
    String provideFileName();

    /**
     * Provides the name for a variable of a given type.
     *
     * @return The name of the variable.
     */
    String provideVariableName();
}
