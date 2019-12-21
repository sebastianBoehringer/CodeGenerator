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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Namespaces collect {@link Element}s that can be identified via their name.
 * <br/>
 * See subclauses 7.4 and 7.8.10 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Namespace extends NamedElement {
    /**
     * References to the owned ElementImports.
     */
    private List<ElementImport> elementImport = new ArrayList<>();
    /**
     * The {@link NamedElement}s this namespace owns. This attribute can be
     * derived.
     */
    private List<NamedElement> ownedMember = new ArrayList<>();
    /**
     * The {@link Constraint}s owned by this namespace.
     */
    private List<Constraint> ownedRule = new ArrayList<>();
    /**
     * The {@link PackageImport}s this namespace owns.
     */
    private List<PackageImport> packageImport = new ArrayList<>();
    /**
     * All {@link NamedElement}s available to this namespace. This includes
     * the imported as well as the owned ones. This attribute can be derived.
     */
    private List<NamedElement> member = new ArrayList<>();
    /**
     * The elements imported into this namespace. This attribute can be derived.
     */
    private List<PackageableElement> importedMember = new ArrayList<>();

    /**
     * Adds a new elementImport to {@link #elementImport}.
     *
     * @param newElementImport The {@link ElementImport} to add
     */
    public void addElementImport(final ElementImport newElementImport) {

        elementImport.add(newElementImport);
    }

    /**
     * Adds a new constraint to {@link #ownedRule}.
     *
     * @param rule The constraint to add
     */
    public void addRule(final Constraint rule) {

        ownedRule.add(rule);
    }

    /**
     * Adds a new element to {@link #member}.
     *
     * @param element The element to add
     */
    public void addMember(final NamedElement element) {

        member.add(element);
    }

    /**
     * Adds a new element to {@link #ownedMember}. The namespace is the owner of
     * this element.
     *
     * @param element The element to add
     */
    public void addOwnedMember(final NamedElement element) {

        ownedMember.add(element);
    }

    /**
     * Adds a new imported element to {@link #importedMember}.
     *
     * @param element The element to add
     */
    public void addImportedMember(final PackageableElement element) {

        importedMember.add(element);
    }

    /**
     * Adds a new packageImport to {@link #packageImport}.
     *
     * @param newPackageImport The packageImport to add
     */
    public void addPackageImport(final PackageImport newPackageImport) {

        packageImport.add(newPackageImport);
    }
}
