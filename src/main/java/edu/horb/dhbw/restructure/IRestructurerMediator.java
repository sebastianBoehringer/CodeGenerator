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
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.classification.Slot;
import edu.horb.dhbw.datacore.uml.classification.Substitution;
import edu.horb.dhbw.datacore.uml.commonstructure.Comment;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.commonstructure.Element;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Enumeration;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.EnumerationLiteral;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.InterfaceRealization;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Connector;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectorEnd;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.datacore.uml.values.Interval;
import edu.horb.dhbw.datacore.uml.values.IntervalConstraint;
import edu.horb.dhbw.restructure.classes.ConnectorEndRestructurer;
import edu.horb.dhbw.restructure.classes.ConnectorRestructurer;
import edu.horb.dhbw.restructure.classes.EnumerationLiteralRestructurer;
import edu.horb.dhbw.restructure.classes.EnumerationRestructurer;
import edu.horb.dhbw.restructure.classes.GeneralizationRestructurer;
import edu.horb.dhbw.restructure.classes.InterfaceRealizationRestructurer;
import edu.horb.dhbw.restructure.classes.InterfaceRestructurer;
import edu.horb.dhbw.restructure.classes.OperationRestructurer;
import edu.horb.dhbw.restructure.classes.ParameterRestructurer;
import edu.horb.dhbw.restructure.classes.PropertyRestructurer;
import edu.horb.dhbw.restructure.classes.SlotRestructurer;
import edu.horb.dhbw.restructure.classes.SubstitutionRestructurer;
import edu.horb.dhbw.restructure.classes.UMLClassRestructurer;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
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
    private static final int DEFAULT_SIZE = 17;

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
    public @NonNull Collection<CommonElements> restructure(
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
     * Will always return an empty optional
     *
     * @param id The id of an element
     * @return {@link Optional#EMPTY}
     */
    @Override
    public Optional<CommonElements> getProcessed(final String id) {

        return Optional.empty();
    }
}
