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

package edu.horb.dhbw.datacore.uml.packages;

import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImport;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageImport;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElementImpl;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The main way to logically group {@link PackageableElement}s.
 * See subclauses 12.2 and 12.4.5 of the UML specification for more details.
 * This should specialize both  {@link Namespace} and
 * {@link PackageableElement}. It currently inherits from just
 * {@link PackageableElement}, the methods and fields from {@link Namespace}
 * have been copied over.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UMLPackageImpl extends PackageableElementImpl
        implements UMLPackage {
    /**
     * An identifier for the package.
     */
    private String uri;
    /**
     * The packages this package contains. This attribute can be derived.
     */
    private List<UMLPackage> nestedPackage = new ArrayList<>();
    /**
     * The package containing this package.
     */
    private UMLPackage nestingPackage;
    /**
     * The types owned by this package. This can be derived.
     */
    private List<Type> ownedType = new ArrayList<>();
    /**
     * The elements owned by this package.
     */
    private List<PackageableElement> packagedElement = new ArrayList<>();
    /**
     * References to the owned ElementImports.
     * Copied from {@link Namespace}.
     */
    private List<ElementImport> elementImport = new ArrayList<>();
    /**
     * The elements imported into this namespace. This attribute can be derived.
     * Copied from {@link Namespace}.
     */
    private List<PackageableElement> importedMember = new ArrayList<>();
    /**
     * All {@link NamedElement}s available to this namespace. This includes
     * the imported as well as the owned ones. This attribute can be derived.
     * Copied from {@link Namespace}.
     */
    private List<NamedElement> member = new ArrayList<>();
    /**
     * The {@link NamedElement}s this namespace owns. This attribute can be
     * derived.
     * Copied from {@link Namespace}.
     */
    private List<NamedElement> ownedMember = new ArrayList<>();
    /**
     * The {@link Constraint}s owned by this namespace.
     * Copied from {@link Namespace}.
     */
    private List<Constraint> ownedRule = new ArrayList<>();
    /**
     * The {@link PackageImport}s this namespace owns.
     */
    private List<PackageImport> packageImport = new ArrayList<>();
}
