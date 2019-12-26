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

package edu.horb.dhbw.datacore.uml.classification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Describes part of the structure of a classifier.
 * See subclauses 9.4 and 9.9.21 of the UML specification for more details.
 * This should specialize
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement},
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.TypedElement} and
 * {@link Feature}. It only inherits from {@link Feature}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class StructuralFeature extends Feature {
    //TODO resolve inheritance
    /**
     * If this is {@code true} the feature cannot be written to after
     * initialization.
     */
    private Boolean isReadOnly = Boolean.FALSE;
}
