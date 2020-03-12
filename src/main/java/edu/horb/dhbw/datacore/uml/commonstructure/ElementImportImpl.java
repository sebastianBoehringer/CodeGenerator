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
import lombok.NoArgsConstructor;

/**
 * Allows an {@link ElementImpl} of a different {@link Namespace} to be
 * referenced in this namespace without a fully qualified name.
 * See subclauses 7.4 and 7.8.7 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ElementImportImpl extends DirectedRelationshipImpl implements ElementImport {
    /**
     * Defines the unqualified name of the imported element. This name should
     * not clash with any names already included in the
     * {@link #importingNamespace}.
     */
    private String alias;
    /**
     * Specifies the visibility of the {@link #importedElement} in the
     * {@link #importingNamespace}.
     */
    private VisibilityKind visibility = VisibilityKind.PUBLIC;
    /**
     * The element that is being imported into this {@link Namespace}.
     */
    private PackageableElement importedElement;
    /**
     * The {@link Namespace} importing the element.
     */
    private Namespace importingNamespace;
}
