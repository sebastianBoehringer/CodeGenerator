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

import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
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
 * {@link Namespace}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UMLPackage extends Namespace {
    //TODO resolve inheritance
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
     * Stereotypes owned by this package. This attribute can be derived.
     */
    private List<Stereotype> ownedStereotype = new ArrayList<>();
    /**
     * The types owned by this package. This can be derived.
     */
    private List<Type> ownedType = new ArrayList<>();
    /**
     * The packageMerges owned by this package.
     */
    private List<PackageMerge> packageMerge = new ArrayList<>();
    /**
     * The elements owned by this package.
     */
    private List<PackageableElement> packagedElement = new ArrayList<>();
    /**
     * The profiles applied to this package.
     */
    private List<ProfileApplication> profileApplication = new ArrayList<>();

    /**
     * Adds a new package to {@link #nestedPackage}.
     *
     * @param umlPackage The package to add
     */
    public void addNestedPackage(final UMLPackage umlPackage) {

        nestedPackage.add(umlPackage);
    }

    /**
     * Adds a new stereotype to {@link #ownedStereotype}.
     *
     * @param stereotype The stereotype to add
     */
    public void addOwnedStereotype(final Stereotype stereotype) {

        ownedStereotype.add(stereotype);
    }

    /**
     * Adds a new type to {@link #ownedType}.
     *
     * @param type The type to add
     */
    public void addOwnedType(final Type type) {

        ownedType.add(type);
    }

    /**
     * Adds a new packageMerge to {@link #packageMerge}.
     *
     * @param merge The packageMerge to add
     */
    public void addPackageMerge(final PackageMerge merge) {

        packageMerge.add(merge);
    }

    /**
     * Adds a new profileApplication to {@link #profileApplication}.
     *
     * @param application The profileApplication to add
     */
    public void addProfileApplication(final ProfileApplication application) {

        profileApplication.add(application);
    }

    /**
     * Adds a new packageableElement to {@link #packagedElement}.
     *
     * @param element The packageableElement to add
     */
    public void addPackagedElement(final PackageableElement element) {

        packagedElement.add(element);
    }
}
