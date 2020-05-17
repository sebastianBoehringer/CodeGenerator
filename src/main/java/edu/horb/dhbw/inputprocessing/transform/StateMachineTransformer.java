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

package edu.horb.dhbw.inputprocessing.transform;

import edu.horb.dhbw.datacore.model.Cardinality;
import edu.horb.dhbw.datacore.model.OOBase;
import edu.horb.dhbw.datacore.model.OOField;
import edu.horb.dhbw.datacore.model.OOMethod;
import edu.horb.dhbw.datacore.model.OOParameter;
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonbehavior.Event;
import edu.horb.dhbw.datacore.uml.commonbehavior.Trigger;
import edu.horb.dhbw.datacore.uml.enums.ParameterDirectionKind;
import edu.horb.dhbw.datacore.uml.enums.PseudostateKind;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.statemachines.Region;
import edu.horb.dhbw.datacore.uml.statemachines.State;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.datacore.uml.statemachines.Transition;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.datacore.uml.values.OpaqueExpression;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.inputprocessing.postvalidate.FirstLetter;
import edu.horb.dhbw.util.Config;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public final class StateMachineTransformer extends
                                           CachingTransformer<StateMachine,
                                                   StateMachineTransformer.ListOOTypeWrapper> {

    /**
     * @param registry The registry to use.
     */
    public StateMachineTransformer(final ITransformerRegistry registry) {

        super(registry);
    }

    @Override
    protected ListOOTypeWrapper doTransformation(
            @NonNull final StateMachine element) {

        Region region = element.getRegion().get(0);
        List<OOType> generatedClasses = new ArrayList<>();
        ITransformer<UMLClass, OOType> typeITransformer =
                getTransformer(UMLClass.class);
        OOType context =
                typeITransformer.transform((UMLClass) element.getContext());
        context.setFields(
                ListUtils.isEmpty(context.getFields()) ? new ArrayList<>()
                                                       : context.getFields());
        OOType baseClass = initBaseClass(context);
        generatedClasses.add(baseClass);

        Set<Event> possibleEvents = new HashSet<>();
        for (Transition transition : region.getTransition()) {
            for (Trigger trigger : transition.getTriggers()) {
                possibleEvents.add(trigger.getEvent());
            }
        }
        for (Event event : possibleEvents) {
            OOMethod methodForBaseClass =
                    createMethod(baseClass, event.getName());
            methodForBaseClass.setLogic(new OOBaseStringWrapper(" "));
            baseClass.getMethods().add(methodForBaseClass);

            OOMethod methodForContext = createMethod(context, event.getName());
            methodForContext.setLogic(new OOBaseStringWrapper("""
                                                                      current_state.%s();      
                                                              """.formatted(
                    methodForContext.getName())));
            context.getMethods().add(methodForContext);
        }
        OOField currentState = new OOField();
        currentState.setType(baseClass);
        currentState.setName("current_state");
        currentState.setVisibility(VisibilityKind.PRIVATE);
        currentState.setCardinality(Cardinality.SINGLE);
        currentState.setParent(context);


        createTransitMethod(context, baseClass);

        ITransformer<Behavior, OOBaseStringWrapper> wrapperITransformer =
                getTransformer(Behavior.class);
        for (State subVertex : region.getSubvertex()) {
            //Disregard any non final pseudoStates

            OOType aClass = new OOType(OOType.Type.CLASS);
            aClass.setId(subVertex.getId());
            aClass.setName(getStateClassName(context, subVertex));
            if (isTargetOfInitial(subVertex)) {
                currentState.setDefaultValue(getStateFieldName(aClass));
            }
            createDefaultBehaviorMethods(aClass, subVertex);
            aClass.setFields(Collections.emptyList());
            if (subVertex.getKind().isPseudoState() && !subVertex.getKind()
                    .isTerminating()) {
                if (isBranchingPseudoState(subVertex.getKind())) {
                    StringBuilder builder = new StringBuilder();
                    ITransformer<Behavior, OOBaseStringWrapper> transformer =
                            getTransformer(Behavior.class);
                    List<Transition> outgoing = subVertex.getOutgoing();
                    for (Transition transition : outgoing) {
                        builder.append("        } else if (");
                        builder.append(
                                extractConditionFromTransition(transition));
                        builder.append(") {\n            ");
                        builder.append(
                                transformer.transform(transition.getEffect())
                                        .getBody());
                        builder.append(String.format("""

                                                                 state_context.transit(state_context.%s);
                                                     """,
                                                     getStateClassName(context,
                                                                       transition
                                                                               .getTarget())));
                    }
                    builder.append("}");
                    builder.replace(8, 15, "");
                    //index 1 will always be doActivity for pseudoStates
                    aClass.getMethods().get(1).setLogic(
                            new OOBaseStringWrapper(builder.toString()));
                    generateMemberStateInContext(context, baseClass, aClass);
                    aClass.setVisibility(VisibilityKind.PACKAGE);
                    aClass.setSuperTypes(Collections.singletonList(baseClass));
                    aClass.setParent(context.getParent());
                    generatedClasses.add(aClass);
                }
                continue;
            }

            for (Transition transition : subVertex.getOutgoing()) {
                for (Trigger trigger : transition.getTriggers()) {
                    OOMethod someMethod =
                            createMethod(aClass, trigger.getEvent().getName());
                    aClass.getMethods().add(someMethod);
                    OOBaseStringWrapper wrapper = wrapperITransformer
                            .transform(transition.getEffect());
                    switch (transition.getKind()) {
                        case INTERNAL -> someMethod.setLogic(wrapper);
                        case LOCAL, EXTERNAL -> {
                            wrapper = wrapper.prepend("        exit();\n")
                                    .append(String.format(
                                            "\n        state_context.transit"
                                            + "(state_context.%s);",
                                            getStateFieldName(aClass)));

                        }
                        default -> throw new IllegalStateException(
                                "Unexpected value: " + transition.getKind());
                    }
                    boolean hasGuard = transition.getGuard() != null;
                    if (hasGuard) {
                        wrapper = wrapper.prepend(") {\n").prepend(
                                extractConditionFromTransition(transition))
                                .prepend("if (");
                        wrapper.append("\n        }");
                    }
                    someMethod.setLogic(wrapper);
                }
            }
            generateMemberStateInContext(context, baseClass, aClass);
            aClass.setVisibility(VisibilityKind.PACKAGE);
            aClass.setSuperTypes(Collections.singletonList(baseClass));
            aClass.setParent(context.getParent());
            generatedClasses.add(aClass);
        }

        context.getFields().add(currentState);
        return new ListOOTypeWrapper(generatedClasses);
    }

    /**
     * @param kind The kind of the pseudoState to check
     * @return {@code True} if the kind is of type
     * {@link PseudostateKind#JUNCTION} or {@link PseudostateKind#CHOICE},
     * {@code false} otherwise.
     */
    private boolean isBranchingPseudoState(final PseudostateKind kind) {

        return kind.equals(PseudostateKind.JUNCTION) || kind
                .equals(PseudostateKind.CHOICE);
    }

    /**
     * @param context   The class which will ultimately own the field
     * @param baseClass The abstractState class
     * @param aClass    The concreteState class
     */
    private void generateMemberStateInContext(final OOType context,
                                              final OOType baseClass,
                                              final OOType aClass) {

        OOField stateInContext = new OOField();
        stateInContext.setParent(context);
        stateInContext.setVisibility(VisibilityKind.PACKAGE);
        stateInContext.setCardinality(Cardinality.SINGLE);
        stateInContext.setType(baseClass);
        stateInContext.setName(getStateFieldName(aClass));
        stateInContext
                .setDefaultValue(String.format("new %s()", aClass.getName()));
        stateInContext.setReadOnly(true);
        context.getFields().add(stateInContext);
    }

    /**
     * Creates the transit method used for transitioning between states.
     * The method is called by concreteStates on the context when they are
     * done with their exit behavior and now want to enter a new state.
     *
     * @param context   The context in which the transit method is created
     * @param baseClass The abstractState class
     */
    private void createTransitMethod(final OOType context,
                                     final OOType baseClass) {

        OOMethod method = createMethod(context, "transit");
        method.setVisibility(VisibilityKind.PACKAGE);
        OOParameter inState = new OOParameter();
        inState.setName("state");
        inState.setType(baseClass);
        inState.setCardinality(Cardinality.SINGLE);
        inState.setDirection(ParameterDirectionKind.IN);
        inState.setParent(method);
        method.setParameters(Collections.singletonList(inState));
        method.setLogic(new OOBaseStringWrapper("""
                                                        current_state = state;
                                                        current_state.entry();
                                                        current_state.doActivity();
                                                """));
        context.getMethods().add(method);
    }

    /**
     * Generates a standard name for the concreteState classes.
     * The result is the concatenation of the context's name, the state's
     * name and {@code "State"}.
     *
     * @param context The context
     * @param state   The state for which the class is generated
     * @return A standardized name
     */
    private String getStateClassName(final OOType context, final State state) {

        FirstLetter legalCasingOfClass =
                Config.CONFIG.getLanguage().getValidationOptions()
                        .getFirstLetterMap().get("class");
        switch (legalCasingOfClass) {

            case UPPER, EITHER -> {
                return replaceFirstWithUppercase(
                        context.getName() + state.getName() + "State");
            }
            case LOWER -> {
                return replaceFirstWithLowercase(
                        context.getName() + state.getName() + "State");
            }
            default -> throw new IllegalStateException(
                    "Unexpected value: " + legalCasingOfClass);
        }
    }

    /**
     * Generates a standard name for the concreteState fields the context
     * class will hold.
     * The value is basically the className but with the first letter cased
     * however the {@link edu.horb.dhbw.datacore.model.ValidationOptions}
     * enforce.
     *
     * @param classType The type of the field name
     * @return A standard name based on the type of the field
     */
    private String getStateFieldName(final OOType classType) {

        FirstLetter legalCasingOfField =
                Config.CONFIG.getLanguage().getValidationOptions()
                        .getFirstLetterMap().get("field");
        switch (legalCasingOfField) {

            case UPPER -> {
                return replaceFirstWithUppercase(classType.getName());
            }
            case LOWER, EITHER -> {
                return replaceFirstWithLowercase(classType.getName());
            }
            default -> throw new IllegalStateException(
                    "Unexpected value: " + legalCasingOfField);
        }
    }

    /**
     * @param string The string to partially uppercase
     * @return The input string but the first character of it is upper cased
     */
    private String replaceFirstWithUppercase(final String string) {

        char firstChar = string.charAt(0);
        if (Character.isUpperCase(firstChar)) {
            return string;
        }
        return Character.toString(firstChar).toUpperCase() + string
                .substring(1);
    }

    /**
     * @param state The state to check
     * @return {@code True} if one of the incoming transitions of the state
     * is originating from an {@link PseudostateKind#INITIAL}.
     */
    private boolean isTargetOfInitial(final State state) {

        return state.getIncoming().stream().anyMatch(
                t -> t.getSource().getKind().equals(PseudostateKind.INITIAL));
    }

    /**
     * @param string The string to partially lowercase
     * @return The input string but the first character of it is lower cased
     */
    private String replaceFirstWithLowercase(final String string) {

        char firstChar = string.charAt(0);
        if (Character.isLowerCase(firstChar)) {
            return string;
        }
        return Character.toString(firstChar).toLowerCase() + string
                .substring(1);
    }

    /**
     * Creates the entry, exit and doActivity methods for the given state.
     *
     * @param state    The state to create the default methods for
     * @param original The state from which the state parameter was created
     */
    private void createDefaultBehaviorMethods(final OOType state,
                                              final State original) {

        OOMethod entry = createMethod(state, "entry");
        OOMethod doActivity = createMethod(state, "doActivity");
        OOMethod exit = createMethod(state, "exit");
        state.setMethods(
                new ArrayList<>(Arrays.asList(entry, doActivity, exit)));
        if (original == null) {
            return;
        }
        ITransformer<Behavior, OOBaseStringWrapper> transformer =
                getTransformer(Behavior.class);
        if (!original.getKind().equals(PseudostateKind.STATE)) {
            state.getMethods().forEach(ooMethod -> ooMethod
                    .setLogic(new OOBaseStringWrapper(" ")));
        } else {
            entry.setLogic(
                    transformer.transform(original.getEntry()).append(" "));
            doActivity.setLogic(transformer.transform(original.getDoActivity())
                                        .append(" "));
            exit.setLogic(
                    transformer.transform(original.getExit()).append(" "));
        }

    }

    /**
     * @param contextClass The context class
     * @return The baseclass
     */
    private OOType initBaseClass(final OOType contextClass) {

        OOType baseClass = new OOType(OOType.Type.CLASS);
        baseClass.setName("Abstract" + contextClass.getName() + "State");
        baseClass.setAbstract(true);
        createDefaultBehaviorMethods(baseClass, null);
        baseClass.getMethods().forEach(ooMethod -> ooMethod.setAbstract(true));

        OOField contextHolder = new OOField();
        contextHolder.setName("state_context");
        contextHolder.setType(contextClass);
        contextHolder.setParent(baseClass);
        contextHolder.setVisibility(VisibilityKind.PROTECTED);
        contextHolder.setCardinality(Cardinality.SINGLE);

        baseClass.setFields(Collections.singletonList(contextHolder));
        baseClass.setVisibility(VisibilityKind.PACKAGE);
        baseClass.setParent(contextClass.getParent());
        return baseClass;
    }

    /**
     * Creates a default public method without any parameters.
     *
     * @param parent The type to add the method to
     * @param name   Name of the method
     * @return The created method
     */
    private OOMethod createMethod(final OOType parent, final String name) {

        OOMethod method = new OOMethod();
        method.setName(name);
        method.setVisibility(VisibilityKind.PUBLIC);
        method.setParent(parent);
        return method;
    }

    @Override
    public @NonNull List<ListOOTypeWrapper> transform(
            @NonNull final List<?> elements) {

        List<StateMachine> classes = new ArrayList<>();
        for (Object e : elements) {
            if (e instanceof StateMachine) {
                classes.add((StateMachine) e);
            }
        }
        return classes.stream().map(this::transform)
                .collect(Collectors.toList());
    }

    /**
     * @param transition The transition to extract the condition from
     * @return The extracted condition
     */
    private String extractConditionFromTransition(final Transition transition) {

        ValueSpecification spec = transition.getGuard().getSpecification();
        if (!(spec instanceof OpaqueExpression)) {
            return "false";
        }
        OpaqueExpression expression = (OpaqueExpression) spec;
        String body;
        int index = expression.getLanguage()
                .indexOf(Config.CONFIG.getLanguage().getName());
        if (index >= 0) {
            body = expression.getBody().get(index);
        } else {
            log.warn("Language [{}] has no corresponding body in opaque "
                     + "expression [{}]", Config.CONFIG.getLanguage().getName(),
                     expression.getId());
            body = expression.getBody().size() > 0 ? expression.getBody().get(0)
                                                   : "false";
        }
        return body;
    }

    public static final class ListOOTypeWrapper extends OOBase {
        /**
         * The wrapped types.
         */
        @NonNull
        private final List<OOType> wrapped;

        /**
         * @param wrappee The types to wrap
         */
        public ListOOTypeWrapper(final List<OOType> wrappee) {

            wrapped = ListUtils.isEmpty(wrappee) ? Collections.emptyList()
                                                 : wrappee;
        }

        /**
         * @return The wrapped types
         */
        @NonNull
        public List<OOType> getWrapped() {

            return wrapped;
        }

        @Override
        protected OOBase getParent() {

            throw new UnsupportedOperationException();
        }
    }
}
