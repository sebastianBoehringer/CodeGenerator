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
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.packages.Extension;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifierImpl;
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
 * {@link BehavioredClassifier}, the fields and added methods from
 * {@link EncapsulatedClassifier} and consequently {@link StructuredClassifier}
 * have been copied over. This is arguably not an elegant solution but will
 * work for the uml specification. The reason is that in the specification no
 * class has an attribute of type {@link StructuredClassifier} or
 * {@link EncapsulatedClassifier}, but there is one of type
 * {@link BehavioredClassifier}, e. g. in
 * {@link edu.horb.dhbw.datacore.uml.simpleclassifiers.InterfaceRealization}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UMLClassImpl extends BehavioredClassifierImpl implements UMLClass {
    {
        setIsAbstract(Boolean.FALSE);
    }
    /**
     * If {@code true} this class is referred to as an active class. Active
     * classes execute their associated behavior immediately upon creation.
     */
    private Boolean isActive = Boolean.FALSE;
    /**
     * If the class is used as a metaclass this specifies the extensions, i
     * e. {@link edu.horb.dhbw.datacore.uml.packages.Stereotype}s, applicable
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
     * The attributes owned by the classifier.
     * Copied from {@link StructuredClassifier}.
     */
    private List<Property> ownedAttribute = new ArrayList<>();
    /**
     * The connectors connected to this classifier.
     * Copied from {@link StructuredClassifier}.
     */
    private List<Connector> ownedConnector = new ArrayList<>();
    /**
     * Instances owned by the classifier via composition, i. e.  properties
     * where {@link Property#isComposite} is {@code true}.
     * Copied from {@link StructuredClassifier}.
     */
    private List<Property> part = new ArrayList<>();
    /**
     * Roles that instances can play in the classifier.
     * Copied from {@link StructuredClassifier}.
     */
    private List<ConnectableElement> role = new ArrayList<>();
    /**
     * The ports owned by the classifier.
     * This attribute can be derived.
     * Copied from {@link EncapsulatedClassifier}.
     */
    private List<Port> ownedPort = new ArrayList<>();
}
