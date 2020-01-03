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

import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.packages.Extension;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.InterfaceRealization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Classifies a set of objects and describes their structure and behavior.
 * See subclauses 11.4 and 11.8.3 of the UML specification for more details.
 * This should specialize both
 * {@link edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier}
 * and {@link EncapsulatedClassifier}. It currently inherits from
 * {@link EncapsulatedClassifier}, the fields and added methods from
 * {@link edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier}
 * have been copied over. This is arguably not a pretty solution as
 * substitution goes down the drain but it has to work.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UMLClass extends EncapsulatedClassifier {
    /**
     * If this is {@code true} the class cannot be instantiated directly.
     */
    private Boolean isAbstract = Boolean.FALSE;
    /**
     * If {@code true} this class is referred to as an active class. Active
     * classes execute their associated behavior immediately upon creation.
     */
    private Boolean isActive = Boolean.FALSE;
    /**
     * If the class is used as a metaclass this specifies the extensions, e.
     * g. {@link edu.horb.dhbw.datacore.uml.packages.Stereotype}s, applicable
     * to it.
     */
    private List<Extension> extension = new ArrayList<>();
    /**
     * Classifiers owned by this class which are not
     * {@link edu.horb.dhbw.datacore.uml.commonbehavior.Behavior}s.
     */
    private List<Classifier> nestedClassifier = new ArrayList<>();
    /**
     * Operations of this class.
     */
    private List<Operation> ownedOperation = new ArrayList<>();
    /**
     * The generalizations of this class. The attribute can be derived.
     */
    private List<UMLClass> superClass = new ArrayList<>();

    /**
     * Describes the behavior of the classifier.
     * Exists to circumvent the issue with multiple inheritance in Java. This
     * field is basically the same as
     * {@link edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier#classifierBehavior}.
     */
    private Behavior classifierBehavior;
    /**
     * The {@link InterfaceRealization} relationships this classifier
     * participates in. This can be seen as a list of interfaces this
     * classifier implements.
     * Exists to circumvent the issue with multiple inheritance in Java. This
     * field is basically the same as
     * {@link edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier#interfaceRealization}.
     */
    private List<InterfaceRealization> interfaceRealization = new ArrayList<>();
    /**
     * Behaviors this classifier owns.
     * Exists to circumvent the issue with multiple inheritance in Java. This
     * field is basically the same as
     * {@link edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier#ownedBehavior}.
     */
    private List<Behavior> ownedBehavior = new ArrayList<>();

    /**
     * Adds a new behavior to {@link #ownedBehavior}.
     *
     * @param behavior The behavior to add
     */
    public void addOwnedBehavior(final Behavior behavior) {

        ownedBehavior.add(behavior);
    }

    /**
     * Adds a new interfaceRealization to {@link #interfaceRealization}.
     *
     * @param realization The interfaceRealization to add
     */
    public void addInterfaceRealization(final InterfaceRealization realization) {

        interfaceRealization.add(realization);
    }

    /**
     * Adds a new classifier to {@link #nestedClassifier}.
     *
     * @param classifier The classifier to add
     */
    public void addNestedClassifier(final Classifier classifier) {

        nestedClassifier.add(classifier);
    }

    /**
     * Adds a new operation to {@link #ownedOperation}.
     *
     * @param operation The operation to add
     */
    public void addOwnedOperation(final Operation operation) {

        ownedOperation.add(operation);
    }

    /**
     * Adds a new class to {@link #superClass}.
     *
     * @param umlClass The class to add
     */
    public void addSuperClass(final UMLClass umlClass) {

        superClass.add(umlClass);
    }

    /**
     * Adds a new extension to {@link #extension}.
     *
     * @param newExtension The extension to add
     */
    public void addExtension(final Extension newExtension) {

        extension.add(newExtension);
    }
}
