/*
 * Copyright (c) 2020 Sebastian Boehringer.
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

package edu.horb.dhbw.util;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.BehavioralFeature;
import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.Feature;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.classification.InstanceSpecification;
import edu.horb.dhbw.datacore.uml.classification.InstanceValue;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.classification.Slot;
import edu.horb.dhbw.datacore.uml.classification.StructuralFeature;
import edu.horb.dhbw.datacore.uml.classification.Substitution;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonbehavior.FunctionBehavior;
import edu.horb.dhbw.datacore.uml.commonbehavior.OpaqueBehavior;
import edu.horb.dhbw.datacore.uml.commonstructure.Abstraction;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.commonstructure.Dependency;
import edu.horb.dhbw.datacore.uml.commonstructure.DirectedRelationship;
import edu.horb.dhbw.datacore.uml.commonstructure.Element;
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImport;
import edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageImport;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Realization;
import edu.horb.dhbw.datacore.uml.commonstructure.Relationship;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.commonstructure.TypedElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Usage;
import edu.horb.dhbw.datacore.uml.packages.Extension;
import edu.horb.dhbw.datacore.uml.packages.ExtensionEnd;
import edu.horb.dhbw.datacore.uml.packages.Model;
import edu.horb.dhbw.datacore.uml.packages.PackageMerge;
import edu.horb.dhbw.datacore.uml.packages.Profile;
import edu.horb.dhbw.datacore.uml.packages.ProfileApplication;
import edu.horb.dhbw.datacore.uml.packages.Stereotype;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.DataType;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Enumeration;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.EnumerationLiteral;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.InterfaceRealization;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.PrimitiveType;
import edu.horb.dhbw.datacore.uml.statemachines.FinalState;
import edu.horb.dhbw.datacore.uml.statemachines.Pseudostate;
import edu.horb.dhbw.datacore.uml.statemachines.Region;
import edu.horb.dhbw.datacore.uml.statemachines.State;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.datacore.uml.statemachines.Transition;
import edu.horb.dhbw.datacore.uml.statemachines.Vertex;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Association;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.AssociationClass;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Collaboration;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.CollaborationUse;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Component;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ComponentRealization;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectableElement;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Connector;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectorEnd;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.EncapsulatedClassifier;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Port;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.StructuredClassifier;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.datacore.uml.values.Expression;
import edu.horb.dhbw.datacore.uml.values.Interval;
import edu.horb.dhbw.datacore.uml.values.IntervalConstraint;
import edu.horb.dhbw.datacore.uml.values.LiteralBoolean;
import edu.horb.dhbw.datacore.uml.values.LiteralInteger;
import edu.horb.dhbw.datacore.uml.values.LiteralNull;
import edu.horb.dhbw.datacore.uml.values.LiteralReal;
import edu.horb.dhbw.datacore.uml.values.LiteralSpecification;
import edu.horb.dhbw.datacore.uml.values.LiteralString;
import edu.horb.dhbw.datacore.uml.values.LiteralUnlimitedNatural;
import edu.horb.dhbw.datacore.uml.values.OpaqueExpression;
import edu.horb.dhbw.datacore.uml.values.StringExpression;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.NonNull;

/**
 * Provides a mapping from the xmi:type to the corresponding java class.
 * For that this class offers different methods to start from a certain
 * ancestor. Not every specialization relationship existing in the uml
 * specification are captured. Furthermore every method matches against the
 * input string with all lowercase. Using
 * {@link XMIUtil#getUMLType(ModelElement)} to generate the input string is
 * recommended.
 */
public final class LookupUtil {

    /**
     * Inaccessible default constructor.
     */
    private LookupUtil() {

    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Realization}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Realization> realizationFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "realization":
                return Realization.class;
            case "substitution":
                return Substitution.class;
            case "interfacerealization":
                return InterfaceRealization.class;
            case "componentrealization":
                return ComponentRealization.class;
            default:
                return null;
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Abstraction}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Abstraction> abstractionFromUMLType(
            @NonNull final String umlType) {

        return "abstraction".equals(umlType) ? Abstraction.class
                                             : realizationFromUMLType(umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Dependency}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Dependency> dependencyFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "usage":
                return Usage.class;
            case "dependency":
                return Dependency.class;
            default:
                return abstractionFromUMLType(umlType);
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link PackageableElement}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends PackageableElement> packageableFromUMLType(
            @NonNull final String umlType) {

        Class<? extends PackageableElement> aClass =
                dependencyFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }
        aClass = packageFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }
        aClass = instanceSpecFromUMLType(umlType);

        if (aClass != null) {
            return aClass;
        }
        aClass = constraintFromUMLType(umlType);

        if (aClass != null) {
            return aClass;
        }
        aClass = typeFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }
        return valueSpecFromUMLType(umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link UMLPackage}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends UMLPackage> packageFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "package":
                return UMLPackage.class;
            case "profile":
                return Profile.class;
            case "model":
                return Model.class;
            default:
                return null;
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending
     * {@link InstanceSpecification}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends InstanceSpecification> instanceSpecFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "instancespecification":
                return InstanceSpecification.class;
            case "enumerationliteral":
                return EnumerationLiteral.class;
            default:
                return null;
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Constraint}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Constraint> constraintFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "constraint":
                return Constraint.class;
            case "intervalconstraint":
                return IntervalConstraint.class;
            default:
                return null;
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Realization}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends ValueSpecification> valueSpecFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "interval":
                return Interval.class;
            case "instancevalue":
                return InstanceValue.class;
            case "opaqueexpression":
                return OpaqueExpression.class;
            default:
                break;
        }
        Class<? extends ValueSpecification> aClass =
                expressionFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }

        return literalSpecFromUMLType(umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Expression}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Expression> expressionFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "expression":
                return Expression.class;
            case "stringexpression":
                return StringExpression.class;
            default:
                return null;
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending
     * {@link LiteralSpecification}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends LiteralSpecification<?>> literalSpecFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "literalreal":
                return LiteralReal.class;
            case "literalunlimitednatural":
                return LiteralUnlimitedNatural.class;
            case "literalstring":
                return LiteralString.class;
            case "literalnull":
                return LiteralNull.class;
            case "literalboolean":
                return LiteralBoolean.class;
            case "literalinteger":
                return LiteralInteger.class;
            default:
                return null;
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.Type}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Type> typeFromUMLType(
            @NonNull final String umlType) {

        return classifierFromUMLType(umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending
     * {@link edu.horb.dhbw.datacore.uml.classification.Classifier}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Classifier> classifierFromUMLType(
            @NonNull final String umlType) {

        if ("interface".equals(umlType)) {
            return Interface.class;
        }
        Class<? extends Classifier> aClass = dataTypeFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }

        aClass = behavioredClassifierFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }
        return structuredClassifierFromUMLType(umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending
     * {@link StructuredClassifier}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends StructuredClassifier> structuredClassifierFromUMLType(
            @NonNull final String umlType) {

        return encapsulatedClassifierFromUMLType(umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending
     * {@link EncapsulatedClassifier}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends EncapsulatedClassifier> encapsulatedClassifierFromUMLType(
            @NonNull final String umlType) {

        return null;
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending
     * {@link BehavioredClassifier}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends BehavioredClassifier> behavioredClassifierFromUMLType(
            @NonNull final String umlType) {

        if ("collaboration".equals(umlType)) {
            return Collaboration.class;
        }
        return classFromUMLType(umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link UMLClass}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends UMLClass> classFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "class":
                return UMLClass.class;
            case "stereotype":
                return Stereotype.class;
            case "component":
                return Component.class;
            default:
                return behaviorFromUMLType(umlType);
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending
     * {@link edu.horb.dhbw.datacore.uml.commonbehavior.Behavior}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Behavior> behaviorFromUMLType(
            @NonNull final String umlType) {

        return "statemachine".equals(umlType) ? StateMachine.class
                                              : opaqueBehaviorFromUMLType(
                                                      umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link OpaqueBehavior}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends OpaqueBehavior> opaqueBehaviorFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "opaquebehavior":
                return OpaqueBehavior.class;
            case "functionbehavior":
                return FunctionBehavior.class;
            default:
                return null;
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link DataType}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends DataType> dataTypeFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "datatype":
                return DataType.class;
            case "primitivetype":
                return PrimitiveType.class;
            case "enumeration":
                return Enumeration.class;
            default:
                return null;
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Vertex}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Vertex> vertexFromUMLType(
            @NonNull final String umlType) {

        return "pseudostate".equals(umlType) ? Pseudostate.class
                                             : stateFromUMLType(umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link State}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends State> stateFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "state":
                return State.class;
            case "finalstate":
                return FinalState.class;
            default:
                return null;
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Namespace}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Namespace> namespaceFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "region":
                return Region.class;
            case "transition":
                return Transition.class;
            default:
                return null;
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link TypedElement}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends TypedElement> typedFromUMLType(
            @NonNull final String umlType) {

        return connectableFromUMLType(umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link ConnectableElement}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends ConnectableElement> connectableFromUMLType(
            @NonNull final String umlType) {

        return "parameter".equals(umlType) ? Parameter.class : null;
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Feature}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Feature> featureFromUMLType(
            @NonNull final String umlType) {

        if ("connector".equals(umlType)) {
            return Connector.class;
        }
        Class<? extends Feature> aClass = structuralFeatureFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }
        return behavioralFeatureFromUMLType(umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link StructuralFeature}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends StructuralFeature> structuralFeatureFromUMLType(
            @NonNull final String umlType) {

        return propertyFromUMLType(umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Property}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Property> propertyFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "property":
                return Property.class;
            case "port":
                return Port.class;
            case "extensionend":
                return ExtensionEnd.class;
            default:
                return null;
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Realization}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends BehavioralFeature> behavioralFeatureFromUMLType(
            @NonNull final String umlType) {

        return "operation".equals(umlType) ? Operation.class : null;
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link NamedElement}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends NamedElement> namedFromUMLType(
            @NonNull final String umlType) {

        if ("collaborationuse".equals(umlType)) {
            return CollaborationUse.class;
        }
        Class<? extends NamedElement> aClass = featureFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }
        aClass = typedFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }
        aClass = namespaceFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }
        aClass = vertexFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }
        return packageableFromUMLType(umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Relationship}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Relationship> relationshipFromUMLType(
            @NonNull final String umlType) {

        Class<? extends Relationship> aClass =
                directedRelationshipFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }
        return associationFromUMLType(umlType);
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending
     * {@link DirectedRelationship}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends DirectedRelationship> directedRelationshipFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "profileapplication":
                return ProfileApplication.class;
            case "packagemerge":
                return PackageMerge.class;
            case "elementimport":
                return ElementImport.class;
            case "generalization":
                return Generalization.class;
            case "packageimport":
                return PackageImport.class;
            default:
                return null;
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Association}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Association> associationFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "extension":
                return Extension.class;
            case "associationclass":
                return AssociationClass.class;
            default:
                return null;
        }
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link MultiplicityElement}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends MultiplicityElement> multiplicityFromUMLType(
            @NonNull final String umlType) {

        return "connectorend".equals(umlType) ? ConnectorEnd.class : null;
    }

    /**
     * Maps the type of an uml class to the corresponding java class.
     * This method specializes on classes extending {@link Element}.
     * This method does NOT trim the namespace.
     *
     * @param umlType The value of the attribute xmi:type
     * @return The class corresponding to this particular type
     */
    public static Class<? extends Element> elementFromUMLType(
            @NonNull final String umlType) {

        switch (umlType) {
            case "slot":
                return Slot.class;
            case "comment":
            default:
                break;
        }
        Class<? extends Element> aClass = relationshipFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }
        aClass = multiplicityFromUMLType(umlType);
        if (aClass != null) {
            return aClass;
        }
        return namedFromUMLType(umlType);
    }
}
