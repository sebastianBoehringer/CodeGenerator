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

import edu.horb.dhbw.inputprocessing.postvalidate.FirstLetter;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
public final class ValidationOptions {
    /**
     * The maximum size of {@link OOType#superTypes} for enums.
     * Corresponds to property {@code validation.enums.maxSuper}.
     */
    private final int enumsMaxSuper;
    /**
     * If this is {@code true} Enumerations of the language can implement
     * interfaces.
     * Corresponds to property {@code validation.enums.canImplementInterfaces}.
     */
    private final boolean enumCanImplementInterface;
    /**
     * The maximum size of {@link OOType#superTypes} for classes.
     * Corresponds to property {@code validation.classes.maxSuper}.
     */
    private final int classesMaxSuper;
    /**
     * The maximum size of {@link OOType#superTypes} for interfaces.
     */
    private final int interfacesMaxSuper;

    /**
     * Maps from the name of a subtype of {@link OOBase} to a
     * {@link FirstLetter} to determine what the first letter of its name
     * has to look like.
     * Encapsulates all properties of the form {@code validation.*.beginsWith}.
     */
    private final Map<String, FirstLetter> firstLetterMap = new HashMap<>();
}
