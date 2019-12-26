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

package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A special case of an {@link Association} where the association itself owns
 * {@link edu.horb.dhbw.datacore.uml.classification.Property}s.
 * See subclauses 11.5 and 11.8.2 of the UML specification for more details.
 * This should specialize both {@link Association} and {@link UMLClass}. It
 * only inherits from {@link Association}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public final class AssociationClass extends Association {
    //TODO resolve inheritance
}
