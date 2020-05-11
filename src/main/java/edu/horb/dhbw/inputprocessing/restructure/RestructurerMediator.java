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

package edu.horb.dhbw.inputprocessing.restructure;


import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.AppliedStereotype;
import edu.horb.dhbw.datacore.uml.XMIElement;
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
import edu.horb.dhbw.datacore.uml.commonbehavior.Event;
import edu.horb.dhbw.datacore.uml.commonbehavior.FunctionBehavior;
import edu.horb.dhbw.datacore.uml.commonbehavior.OpaqueBehavior;
import edu.horb.dhbw.datacore.uml.commonbehavior.Trigger;
import edu.horb.dhbw.datacore.uml.commonstructure.Abstraction;
import edu.horb.dhbw.datacore.uml.commonstructure.Comment;
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
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.DataType;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Enumeration;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.EnumerationLiteral;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.InterfaceRealization;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.PrimitiveType;
import edu.horb.dhbw.datacore.uml.statemachines.Region;
import edu.horb.dhbw.datacore.uml.statemachines.State;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.datacore.uml.statemachines.Transition;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Component;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ComponentRealization;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectableElement;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Connector;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectorEnd;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.EncapsulatedClassifier;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Port;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.StructuredClassifier;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
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
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.inputprocessing.restructure.classes.AbstractionRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.ConnectorEndRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.ConnectorRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.DatatypeRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.DependencyRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.EnumerationLiteralRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.EnumerationRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.GeneralizationRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.GeneralizationSetRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.InstanceSpecRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.InstanceValueRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.InterfaceRealizationRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.InterfaceRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.LiteralBoolRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.LiteralIntRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.LiteralNullRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.LiteralRealRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.LiteralStringRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.LiteralUnlimitedNaturalRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.OperationRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.ParameterRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.PrimitiveRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.PropertyRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.RealizationRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.SlotRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.SubstitutionRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.classes.UMLClassRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.components.ComponentRealizationRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.components.ComponentRestrucuturer;
import edu.horb.dhbw.inputprocessing.restructure.components.PortRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.components.UsageRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.BehaviorRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.BehavioralFeatureRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.BehavioredClassifierRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.ClassifierRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.ConnectableRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.DirectedRelationshipRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.ElementRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.EncapsulatedClassifierRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.FeatureRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.LiteralSpecRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.MultElementRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.NamedElementRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.NamespaceRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.PackageableRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.RelationshipRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.StructuralFeatureRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.StructuredClassifierRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.TypeRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.TypedElementRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.delegating.ValueSpecRestrucuturer;
import edu.horb.dhbw.inputprocessing.restructure.packaging.ElementImportRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.packaging.ModelRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.packaging.PackageImportRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.packaging.PackageRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.statemachines.EventRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.statemachines.FunctionBehaviorRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.statemachines.OpaqueBehaviorRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.statemachines.RegionRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.statemachines.StateMachineRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.statemachines.StateRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.statemachines.TransitionRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.statemachines.TriggerRestructurer;
import edu.horb.dhbw.util.Caching;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public final class RestructurerMediator
        implements IRestructurerMediator, Caching {

    /**
     * The number of {@link IRestructurer}s registered when using the default
     * constructor.
     */
    private static final int DEFAULT_SIZE = 70;

    /**
     * The mappings to use.
     */
    private final Map<Class<? extends XMIElement>, IRestructurer<?
            extends XMIElement>>
            classToRestructurer;

    /**
     * The default {@link IRestructurer} to use if no specialized one is
     * registered in {@link #classToRestructurer}.
     */
    private final IRestructurer<? extends XMIElement> defaultImplementation =
            new NoopRestructurer(this);

    /**
     * Default Constructor.
     * Initializes {@link #classToRestructurer} with some default
     * {@link IRestructurer}s.
     */
    public RestructurerMediator() {

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
        ElementRestructurer elementRestructurer = new ElementRestructurer(this);
        classToRestructurer.put(Element.class, elementRestructurer);
        classToRestructurer.put(XMIElement.class, elementRestructurer);
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
                .put(PrimitiveType.class, new PrimitiveRestructurer(this));
        classToRestructurer.put(DataType.class, new DatatypeRestructurer(this));
        classToRestructurer.put(State.class, new StateRestructurer(this));
        classToRestructurer
                .put(Transition.class, new TransitionRestructurer(this));
        classToRestructurer
                .put(StateMachine.class, new StateMachineRestructurer(this));
        classToRestructurer.put(Region.class, new RegionRestructurer(this));
        classToRestructurer.put(LiteralUnlimitedNatural.class,
                                new LiteralUnlimitedNaturalRestructurer(this));
        classToRestructurer
                .put(LiteralString.class, new LiteralStringRestructurer(this));
        classToRestructurer
                .put(LiteralReal.class, new LiteralRealRestructurer(this));
        classToRestructurer
                .put(LiteralNull.class, new LiteralNullRestructurer(this));
        classToRestructurer
                .put(LiteralInteger.class, new LiteralIntRestructurer(this));
        classToRestructurer
                .put(LiteralBoolean.class, new LiteralBoolRestructurer(this));
        classToRestructurer
                .put(ElementImport.class, new ElementImportRestructurer(this));
        classToRestructurer
                .put(PackageImport.class, new PackageImportRestructurer(this));
        classToRestructurer
                .put(UMLPackage.class, new PackageRestructurer(this));
        classToRestructurer.put(edu.horb.dhbw.datacore.uml.packages.Model.class,
                                new ModelRestructurer(this));
        classToRestructurer.put(ComponentRealization.class,
                                new ComponentRealizationRestructurer(this));
        classToRestructurer
                .put(Realization.class, new RealizationRestructurer(this));
        classToRestructurer.put(StructuredClassifier.class,
                                new StructuredClassifierRestructurer(this));
        classToRestructurer.put(OpaqueExpression.class,
                                new OpaqueExpressionRestructurer(this));
        classToRestructurer
                .put(Component.class, new ComponentRestrucuturer(this));
        classToRestructurer.put(Port.class, new PortRestructurer(this));
        classToRestructurer.put(AppliedStereotype.class,
                                new AppliedStereotypeRestructurer(this));
        classToRestructurer.put(Trigger.class, new TriggerRestructurer(this));
        classToRestructurer.put(Event.class, new EventRestructurer(this));
    }

    /**
     * @param clazz        The class for which a restructurer is added
     * @param restructurer The restructurer to add
     * @param <T>          Ensuring that the class and the restructurer match
     */
    public <T extends XMIElement> void register(
            @NonNull final Class<T> clazz,
            @NonNull final IRestructurer<T> restructurer) {

        classToRestructurer.put(clazz, restructurer);
    }

    @Override
    public <T extends XMIElement> void remove(@NonNull final Class<T> tClass) {

        classToRestructurer.remove(tClass);
    }

    /**
     * @param clazz The class to restructure to
     * @param <T>   The class that the IRestructurer restructures to. Upper
     *              bound is {@link XMIElement}.
     * @return An IRestructurer transforming a
     * {@link com.sdmetrics.model.Model} to a T
     */
    @NonNull
    public <T extends XMIElement> IRestructurer<T> getIRestructurer(
            @NonNull final Class<T> clazz) {

        return (IRestructurer<T>) classToRestructurer
                .getOrDefault(clazz, defaultImplementation);
    }

    /**
     * Restructures the given {@link Model}.
     * This calls {@link IRestructurer#restructure(Model)} on every
     * {@link IRestructurer} contained in {@link #classToRestructurer}.
     *
     * @param model The model to restructure
     * @return A list of the restructured uml classes
     */
    @Override
    public @NonNull List<XMIElement> restructure(@NonNull final Model model) {

        List<XMIElement> elements = new ArrayList<>();
        for (Map.Entry<Class<? extends XMIElement>, IRestructurer<?
                extends XMIElement>> entry : classToRestructurer
                .entrySet()) {
            elements.addAll(entry.getValue().restructure(model));
        }
        return elements;
    }

    /**
     * Restructures the given {@link ModelElement}.
     * This works by looking up the correct class for the
     * {@link ModelElement} via
     * {@link IRestructurer#canRestructure(ModelElement)} and calling the
     * appropriate {@link IRestructurer} implementation.
     *
     * @param element The modelElement to restructure
     * @return The restructured element
     */
    @Override
    public XMIElement restructure(@NonNull final ModelElement element) {

        IRestructurer<? extends XMIElement> restructurer =
                classToRestructurer.values().stream().
                        filter(it -> it.canRestructure(element)).findFirst()
                        .orElse(defaultImplementation);
        return restructurer.restructure(element);
    }

    /**
     * Cleans up the caches of every registered
     * {@link AbstractCachingRestructurer}.
     */
    @Override
    public void readyForNextRestructuring() {

        for (IRestructurer<?> value : classToRestructurer.values()) {
            if (value instanceof Caching) {
                ((Caching) value).cleanCache();
            }
        }
    }

    /**
     * @param base    The object to add the general attributes to.
     * @param element The modelelement holding the information
     * @param <S>     A subclass of {@link XMIElement}.
     * @return Will always return base unchanged.
     */
    @Override
    public <S extends XMIElement> S restructure(final @NonNull S base,
                                                final @NonNull ModelElement element) {

        return base;
    }

    /**
     * @param element The element to check
     * @return {@code True} if any of the registered {@link IRestructurer}s
     * in {@link #classToRestructurer} can process the given element, {@code
     * false} otherwise
     */
    @Override
    public boolean canRestructure(final @NonNull ModelElement element) {

        return classToRestructurer.values().stream()
                .anyMatch(it -> it.canRestructure(element));
    }

    @Override
    public void cleanCache() {

        readyForNextRestructuring();
    }
}
