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
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BehavioralFeatureImpl extends FeatureImpl
        implements BehavioralFeature {
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
    private List<Behavior> method = new ArrayList<>();
    /**
     * The parameters for this feature.
     */
    private List<Parameter> ownedParameter = new ArrayList<>();
    /**
     * The exceptions this feature can raise when it is invoked.
     */
    private List<Type> raisedException = new ArrayList<>();
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
}
