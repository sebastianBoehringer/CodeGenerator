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

import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImport;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageImport;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.enums.CallConcurrencyKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Specifies an aspect of the behavior of the owning {@link Classifier}.
 * See subclauses 9.4 and 9.9.2 of the UML specification for more details.
 * This should be a specialization of {@link Feature} and
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}. It currently
 * only inherits from {@link Feature}, the fields and methods special to
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace} have been
 * copied over.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BehavioralFeature extends Feature {
    /**
     * Specifies the semantics of concurrent calls.
     */
    private CallConcurrencyKind concurrency = CallConcurrencyKind.SEQUENTIAL;
    /**
     * If this attribute is {@code true}, the behavioralFeature has no
     * implementation.
     */
    private Boolean isAbstract = Boolean.FALSE;
    /**
     * The Behavior that implements the feature.
     */
    private List<Behavior> method;
    /**
     * The parameters for this feature.
     */
    private List<Parameter> ownedParameter = new ArrayList<>();
    /**
     * The exceptions this feature can raise when it is invoked.
     */
    private List<Type> raisedException = new ArrayList<>();

    /**
     * Adds a new type of exception to {@link #raisedException}.
     *
     * @param exception The type of exception to add
     */
    public void addException(final Type exception) {

        raisedException.add(exception);
    }

    /**
     * Adds a new parameter to {@link #ownedParameter}.
     *
     * @param parameter The parameter to add
     */
    public void addParameter(final Parameter parameter) {

        ownedParameter.add(parameter);
    }

    /**
     * Adds a new behavior to {@link #method}.
     *
     * @param behavior The behavior to add
     */
    public void addMethod(final Behavior behavior) {

        method.add(behavior);
    }

    /**
     * References to the owned ElementImports.
     * Copied from {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}.
     */
    private List<ElementImport> elementImport = new ArrayList<>();
    /**
     * The elements imported into this namespace. This attribute can be derived.
     * Copied from {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}.
     */
    private List<PackageableElement> importedMember = new ArrayList<>();
    /**
     * All {@link NamedElement}s available to this namespace. This includes
     * the imported as well as the owned ones. This attribute can be derived.
     * Copied from {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}.
     */
    private List<NamedElement> member = new ArrayList<>();
    /**
     * The {@link NamedElement}s this namespace owns. This attribute can be
     * derived.
     * Copied from {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}.
     */
    private List<NamedElement> ownedMember = new ArrayList<>();
    /**
     * The {@link Constraint}s owned by this namespace.
     * Copied from {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}.
     */
    private List<Constraint> ownedRule = new ArrayList<>();
    /**
     * The {@link PackageImport}s this namespace owns.
     * Copied from {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}.
     */
    private List<PackageImport> packageImport = new ArrayList<>();

    /**
     * Adds a new elementImport to {@link #elementImport}.
     * Copied from {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}.
     *
     * @param newElementImport The {@link ElementImport} to add
     */
    public void addElementImport(final ElementImport newElementImport) {

        elementImport.add(newElementImport);
    }

    /**
     * Adds a new constraint to {@link #ownedRule}.
     * Copied from {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}.
     *
     * @param rule The constraint to add
     */
    public void addRule(final Constraint rule) {

        ownedRule.add(rule);
    }

    /**
     * Adds a new element to {@link #member}.
     * Copied from {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}.
     *
     * @param element The element to add
     */
    public void addMember(final NamedElement element) {

        member.add(element);
    }

    /**
     * Adds a new element to {@link #ownedMember}. The namespace is the owner of
     * this element.
     * Copied from {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}.
     *
     * @param element The element to add
     */
    public void addOwnedMember(final NamedElement element) {

        ownedMember.add(element);
    }

    /**
     * Adds a new imported element to {@link #importedMember}.
     * Copied from {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}.
     *
     * @param element The element to add
     */
    public void addImportedMember(final PackageableElement element) {

        importedMember.add(element);
    }

    /**
     * Adds a new packageImport to {@link #packageImport}.
     * Copied from {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}.
     *
     * @param newPackageImport The packageImport to add
     */
    public void addPackageImport(final PackageImport newPackageImport) {

        packageImport.add(newPackageImport);
    }


}
