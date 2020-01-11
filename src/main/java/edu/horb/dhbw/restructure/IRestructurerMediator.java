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

package edu.horb.dhbw.restructure;


import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.CommonElements;
import edu.horb.dhbw.datacore.uml.classification.BehavioralFeature;
import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.Feature;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.classification.GeneralizationSet;
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
import edu.horb.dhbw.datacore.uml.commonstructure.Comment;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.commonstructure.Dependency;
import edu.horb.dhbw.datacore.uml.commonstructure.DirectedRelationship;
import edu.horb.dhbw.datacore.uml.commonstructure.Element;
import edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Relationship;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.commonstructure.TypedElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Usage;
import edu.horb.dhbw.datacore.uml.packages.Stereotype;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.DataType;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Enumeration;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.EnumerationLiteral;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.InterfaceRealization;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.PrimitiveType;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectableElement;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Connector;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectorEnd;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.EncapsulatedClassifier;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.datacore.uml.values.Interval;
import edu.horb.dhbw.datacore.uml.values.IntervalConstraint;
import edu.horb.dhbw.datacore.uml.values.LiteralSpecification;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.restructure.classes.AbstractionRestructurer;
import edu.horb.dhbw.restructure.classes.ConnectorEndRestructurer;
import edu.horb.dhbw.restructure.classes.ConnectorRestructurer;
import edu.horb.dhbw.restructure.classes.DatatypeRestructurer;
import edu.horb.dhbw.restructure.classes.DependencyRestructurer;
import edu.horb.dhbw.restructure.classes.EnumerationLiteralRestructurer;
import edu.horb.dhbw.restructure.classes.EnumerationRestructurer;
import edu.horb.dhbw.restructure.classes.GeneralizationRestructurer;
import edu.horb.dhbw.restructure.classes.GeneralizationSetRestructurer;
import edu.horb.dhbw.restructure.classes.InstanceSpecRestructurer;
import edu.horb.dhbw.restructure.classes.InstanceValueRestructurer;
import edu.horb.dhbw.restructure.classes.InterfaceRealizationRestructurer;
import edu.horb.dhbw.restructure.classes.InterfaceRestructurer;
import edu.horb.dhbw.restructure.classes.OperationRestructurer;
import edu.horb.dhbw.restructure.classes.ParameterRestructurer;
import edu.horb.dhbw.restructure.classes.PrimitiveRestructurer;
import edu.horb.dhbw.restructure.classes.PropertyRestructurer;
import edu.horb.dhbw.restructure.classes.SlotRestructurer;
import edu.horb.dhbw.restructure.classes.StereotypeRestructurer;
import edu.horb.dhbw.restructure.classes.SubstitutionRestructurer;
import edu.horb.dhbw.restructure.classes.UMLClassRestructurer;
import edu.horb.dhbw.restructure.components.UsageRestructurer;
import edu.horb.dhbw.restructure.delegating.BehaviorRestructurer;
import edu.horb.dhbw.restructure.delegating.BehavioralFeatureRestructurer;
import edu.horb.dhbw.restructure.delegating.BehavioredClassifierRestructurer;
import edu.horb.dhbw.restructure.delegating.ClassifierRestructurer;
import edu.horb.dhbw.restructure.delegating.ConnectableRestructurer;
import edu.horb.dhbw.restructure.delegating.DirectedRelationshipRestructurer;
import edu.horb.dhbw.restructure.delegating.ElementRestructurer;
import edu.horb.dhbw.restructure.delegating.EncapsulatedClassifierRestructurer;
import edu.horb.dhbw.restructure.delegating.FeatureRestructurer;
import edu.horb.dhbw.restructure.delegating.LiteralSpecRestructurer;
import edu.horb.dhbw.restructure.delegating.MultElementRestructurer;
import edu.horb.dhbw.restructure.delegating.NamedElementRestructurer;
import edu.horb.dhbw.restructure.delegating.NamespaceRestructurer;
import edu.horb.dhbw.restructure.delegating.PackageableRestructurer;
import edu.horb.dhbw.restructure.delegating.RelationshipRestructurer;
import edu.horb.dhbw.restructure.delegating.StructuralFeatureRestructurer;
import edu.horb.dhbw.restructure.delegating.TypeRestructurer;
import edu.horb.dhbw.restructure.delegating.TypedElementRestructurer;
import edu.horb.dhbw.restructure.delegating.ValueSpecRestrucuturer;
import edu.horb.dhbw.restructure.statemachines.FunctionBehaviorRestructurer;
import edu.horb.dhbw.restructure.statemachines.OpaqueBehaviorRestructurer;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class IRestructurerMediator implements IRestructurer<CommonElements> {

    /**
     * The number of {@link IRestructurer}s registered when using the default
     * constructor.
     */
    private static final int DEFAULT_SIZE = 39;

    /**
     * The mappings to use.
     */
    private final Map<Class<? extends CommonElements>, IRestructurer<?
            extends CommonElements>>
            classToRestructurer;

    /**
     * The default {@link IRestructurer} to use if no specialized one is
     * registered in {@link #classToRestructurer}.
     */
    private final IRestructurer<? extends Element> defaultImplementation =
            new IRestructurerDefImpl(this);

    /**
     * Default Constructor.
     * Initializes {@link #classToRestructurer} with some default
     * {@link IRestructurer}s.
     */
    public IRestructurerMediator() {

        classToRestructurer = new HashMap<>(DEFAULT_SIZE);
        classToRestructurer.put(UMLClass.class, new UMLClassRestructurer(this));
        classToRestructurer
                .put(Connector.class, new ConnectorRestructurer(this));
        classToRestructurer.put(Generalization.class,
                                new GeneralizationRestructurer(this));
        classToRestructurer.put(InterfaceRealization.class,
                                new InterfaceRealizationRestructurer(this));
        classToRestructurer.put(Property.class, new PropertyRestructurer(this));
        classToRestructurer
                .put(Operation.class, new OperationRestructurer(this));
        classToRestructurer
                .put(Parameter.class, new ParameterRestructurer(this));
        classToRestructurer
                .put(Interface.class, new InterfaceRestructurer(this));
        classToRestructurer
                .put(Enumeration.class, new EnumerationRestructurer(this));
        classToRestructurer.put(EnumerationLiteral.class,
                                new EnumerationLiteralRestructurer(this));
        classToRestructurer
                .put(Substitution.class, new SubstitutionRestructurer(this));
        classToRestructurer.put(Slot.class, new SlotRestructurer(this));
        classToRestructurer
                .put(Constraint.class, new ConstraintRestrucuturer(this));
        classToRestructurer
                .put(ConnectorEnd.class, new ConnectorEndRestructurer(this));
        classToRestructurer.put(IntervalConstraint.class,
                                new IntervalConstraintRestrucuturer(this));
        classToRestructurer.put(Comment.class, new CommentRestructurer(this));
        classToRestructurer.put(Interval.class, new IntervalRestructurer(this));
        classToRestructurer.put(Type.class, new TypeRestructurer(this));
        classToRestructurer.put(BehavioralFeature.class,
                                new BehavioralFeatureRestructurer(this));
        classToRestructurer
                .put(Classifier.class, new ClassifierRestructurer(this));
        classToRestructurer.put(Feature.class, new FeatureRestructurer(this));
        classToRestructurer.put(StructuralFeature.class,
                                new StructuralFeatureRestructurer(this));
        classToRestructurer.put(Behavior.class, new BehaviorRestructurer(this));
        classToRestructurer.put(DirectedRelationship.class,
                                new DirectedRelationshipRestructurer(this));
        classToRestructurer.put(Element.class, new ElementRestructurer(this));
        classToRestructurer.put(MultiplicityElement.class,
                                new MultElementRestructurer(this));
        classToRestructurer
                .put(NamedElement.class, new NamedElementRestructurer(this));
        classToRestructurer
                .put(Namespace.class, new NamespaceRestructurer(this));
        classToRestructurer.put(PackageableElement.class,
                                new PackageableRestructurer(this));
        classToRestructurer
                .put(Relationship.class, new RelationshipRestructurer(this));
        classToRestructurer
                .put(TypedElement.class, new TypedElementRestructurer(this));
        classToRestructurer.put(BehavioredClassifier.class,
                                new BehavioredClassifierRestructurer(this));
        classToRestructurer.put(EncapsulatedClassifier.class,
                                new EncapsulatedClassifierRestructurer(this));
        classToRestructurer.put(ConnectableElement.class,
                                new ConnectableRestructurer(this));
        classToRestructurer.put(ValueSpecification.class,
                                new ValueSpecRestrucuturer(this));
        classToRestructurer.put(LiteralSpecification.class,
                                new LiteralSpecRestructurer(this));
        classToRestructurer.put(GeneralizationSet.class,
                                new GeneralizationSetRestructurer(this));
        classToRestructurer.put(InstanceSpecification.class,
                                new InstanceSpecRestructurer(this));
        classToRestructurer
                .put(InstanceValue.class, new InstanceValueRestructurer(this));
        classToRestructurer.put(OpaqueBehavior.class,
                                new OpaqueBehaviorRestructurer(this));
        classToRestructurer.put(FunctionBehavior.class,
                                new FunctionBehaviorRestructurer(this));
        classToRestructurer
                .put(Abstraction.class, new AbstractionRestructurer(this));
        classToRestructurer
                .put(Dependency.class, new DependencyRestructurer(this));
        classToRestructurer.put(Usage.class, new UsageRestructurer(this));
        classToRestructurer
                .put(Stereotype.class, new StereotypeRestructurer(this));
        classToRestructurer
                .put(PrimitiveType.class, new PrimitiveRestructurer(this));
        classToRestructurer.put(DataType.class, new DatatypeRestructurer(this));

    }

    /**
     * @param clazz        The class for which a restructurer is added
     * @param restructurer The restructurer to add
     * @param <T>          Ensuring that the class and the restructurer match
     */
    public <T extends CommonElements> void register(
            @NonNull final Class<T> clazz,
            @NonNull final IRestructurer<T> restructurer) {

        classToRestructurer.put(clazz, restructurer);
    }

    /**
     * @param clazz The class to restructure to
     * @param <T>   The class that the IRestructurer restructures to. Upper
     *              bound is {@link CommonElements}.
     * @return An IRestructurer transforming a
     * {@link com.sdmetrics.model.Model} to a T
     */
    @NonNull
    public <T extends CommonElements> IRestructurer<T> getIRestructurer(
            @NonNull final Class<T> clazz) {

        return (RestructurerBase<T>) classToRestructurer
                .getOrDefault(clazz, defaultImplementation);
    }

    @Override
    public @NonNull List<CommonElements> restructure(
            @NonNull final Model model) {

        List<CommonElements> elements = new ArrayList<>();
        for (Map.Entry<Class<? extends CommonElements>, IRestructurer<?
                extends CommonElements>> entry : classToRestructurer
                .entrySet()) {
            elements.addAll(entry.getValue().restructure(model));
        }
        return elements;
    }

    @Override
    public CommonElements restructure(@NonNull final ModelElement element) {

        Class<? extends CommonElements> clazz =
                LookupUtil.elementFromUMLType(XMIUtil.getUMLType(element));
        return classToRestructurer.getOrDefault(clazz, defaultImplementation)
                .restructure(element);
    }

    /**
     * Always returns false.
     * The reason for that is that the mediator does not cache intermediate
     * results.
     *
     * @param id The id of an element
     * @return {@code false}
     */
    @Override
    public boolean wasProcessed(final String id) {

        return false;
    }

    /**
     * Will always return an empty optional.
     *
     * @param id The id of an element
     * @return {@link Optional#EMPTY}
     */
    @Override
    public Optional<CommonElements> getProcessed(final String id) {

        return Optional.empty();
    }
}
