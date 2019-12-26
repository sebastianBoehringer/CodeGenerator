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

package edu.horb.dhbw.datacore.uml.commonstructure;

import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * An {@link Element} that can be owned by an
 * {@link edu.horb.dhbw.datacore.uml.packages.UMLPackage}.
 * See subclauses 7.4 and 7.8.12 of the UML specification for more details.
 * The only differences besides the added semantic meaning is that
 * {@link #getVisibility()} defaults to {@link VisibilityKind#PUBLIC}.
 * {@link NamedElement} does not set a default value.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public abstract class PackageableElement extends NamedElement {

    {
        setVisibility(VisibilityKind.PUBLIC);
    }
}
