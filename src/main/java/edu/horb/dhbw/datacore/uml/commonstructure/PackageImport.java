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
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a relation that imports all elements with a
 * {@link VisibilityKind} that is not {@link VisibilityKind#PRIVATE} into the
 * owning {@link Namespace}.
 * <br/>
 * See subclauses 7.4 and 7.8.11 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageImport extends DirectedRelationship {
    /**
     * The visibility of the imported elements in the
     * {@link #importingNamespace}.
     */
    private VisibilityKind visibility = VisibilityKind.PUBLIC;
    /**
     * The package that is imported into the namespace.
     */
    private UMLPackage importedPackage;
    /**
     * The namespace that is importing the package.
     */
    private Namespace importingNamespace;

}

