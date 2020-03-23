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

import edu.horb.dhbw.datacore.model.ChoiceStatement;
import edu.horb.dhbw.datacore.model.FunctionCallStatement;
import edu.horb.dhbw.datacore.model.IStatement;
import edu.horb.dhbw.datacore.model.LoopStatement;
import edu.horb.dhbw.datacore.model.OOLogic;
import edu.horb.dhbw.datacore.model.OOMethod;
import edu.horb.dhbw.datacore.model.OpaqueStatement;
import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonbehavior.OpaqueBehavior;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.enums.PseudostateKind;
import edu.horb.dhbw.datacore.uml.statemachines.Region;
import edu.horb.dhbw.datacore.uml.statemachines.State;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.datacore.uml.statemachines.Transition;
import edu.horb.dhbw.datacore.uml.values.IntervalConstraint;
import edu.horb.dhbw.datacore.uml.values.OpaqueExpression;
import edu.horb.dhbw.util.Config;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public final class OOLogicTransformer
        extends CachingTransformer<StateMachine, OOLogic> {
    /**
     * @param registry The registry to use.
     */
    public OOLogicTransformer(final TransformerRegistry registry) {

        super(registry);
    }

    @Override
    protected OOLogic doTransformation(final @NonNull StateMachine element) {

        if (element.getRegion().size() == 1) {
            State startState =
                    element.getRegion().get(0).getSubvertex().stream()
                            .filter(v -> v.getKind()
                                    .equals(PseudostateKind.INITIAL))
                            .findFirst().orElse(null);
            if (startState == null) {
                throw new IllegalArgumentException(String.format(
                        "Statemachine %s has a single region but no initial "
                                + "state", element.getId()));
            }
            Pair<State, List<IStatement>> pair =
                    handleIsolatedProgression(startState);
            if (!pair.first().getKind().isTerminating()) {
                throw new IllegalArgumentException(
                        "A statemachine with a single region "
                                + "should not have pseudostates "
                                + "of types join as they serve "
                                + "no purpose");
            }
            return new OOLogic(pair.second());
        }

        //TODO case of multiple regions
        return new OOLogic(Collections.emptyList());
    }

    @Override
    public @NonNull List<OOLogic> transform(final @NonNull List<?> elements) {

        List<StateMachine> machines = new ArrayList<>();
        for (Object e : elements) {
            if (e instanceof StateMachine) {
                machines.add((StateMachine) e);
            }
        }
        return machines.stream().map(super::transform)
                .collect(Collectors.toList());
    }

    private List<State> findInitialStates(final StateMachine stateMachine) {

        List<State> initialStates = new ArrayList<>();
        for (Region region : stateMachine.getRegion()) {
            for (State state : region.getSubvertex()) {
                if (state.getKind().equals(PseudostateKind.INITIAL)) {
                    initialStates.add(state);
                    break;
                }
            }
        }

        return initialStates;
    }

    private List<IStatement> extractTransitionBehavior(final Transition transition) {

        if (transition.getEffect() == null) {
            log.debug("Transition [{}] had no associated behavior",
                      transition.getId());
            return Collections.emptyList();
        }
        log.debug("Extracting behavior of transition [{}]", transition.getId());
        Behavior effect = transition.getEffect();
        List<IStatement> statement = getStatementsFromBehavior(effect);
        return statement.size() > 0 ? statement : Collections.emptyList();
    }

    private List<IStatement> getStatementsFromBehavior(final Behavior behavior) {

        if (behavior instanceof OpaqueBehavior) {
            OpaqueBehavior effect = (OpaqueBehavior) behavior;
            //TODO use language
            return Collections.singletonList(
                    new OpaqueStatement(effect.getBody().get(0)));
        }
        if (behavior instanceof StateMachine) {
            OOLogic spec = super.transform(((StateMachine) behavior));
            return spec != null ? spec.getStatements()
                                : Collections.emptyList();
        }
        log.error("There should not be another class that extends Behavior "
                          + "but [{}] does",
                  behavior.getClass().getSimpleName());
        return Collections.emptyList();
    }

    /**
     * Converts the standard {@link Behavior}s a {@link State} can own to a
     * list of {@link IStatement}s.
     *
     * @param state The state to extract the behavior from
     * @return The statements used to execute {@link State#entry},
     * {@link State#doActivity} and {@link State#exit} in that order.
     */
    private List<IStatement> extractStateBehavior(final State state) {

        log.debug("Extracting behavior for state [{}]", state.getId());
        if (state.getKind() != PseudostateKind.STATE) {
            //PseudoStates do not have behaviors associated with them
            return Collections.emptyList();
        }
        List<IStatement> statements = new ArrayList<>();
        if (state.getEntry() != null) {
            List<IStatement> statement =
                    getStatementsFromBehavior(state.getEntry());
            if (statement != null) {
                statements.addAll(statement);
            }
        }
        if (state.getDoActivity() != null) {
            List<IStatement> statement =
                    getStatementsFromBehavior(state.getDoActivity());
            if (statement != null) {
                statements.addAll(statement);
            }
        }
        if (state.getSubmachine() != null) {
            ITransformer<Operation, OOMethod> methodITransformer =
                    getTransformer(Operation.class);
            List<OOMethod> method;
            method = methodITransformer.transform(Collections.singletonList(
                    state.getSubmachine().getSpecification()));
            if (method.size() > 0) {
                statements.add(new FunctionCallStatement("TODO", method));
            }
        }
        if (state.getExit() != null) {
            List<IStatement> statement =
                    getStatementsFromBehavior(state.getExit());
            if (statement != null) {
                statements.addAll(statement);
            }
        }
        log.debug("Finished extracting behavior for state [{}]", state.getId());
        return statements.size() > 0 ? statements : Collections.emptyList();
    }

    private boolean allCompleted(final List<State> states) {

        for (State state : states) {
            if (!state.getKind().equals(PseudostateKind.FINAL)) {
                return false;
            }
        }
        return true;
    }

    private Map<String, State> getAllStates(final StateMachine machine) {

        Map<String, State> states = new HashMap<>();
        machine.getRegion().forEach(
                r -> r.getSubvertex().forEach(v -> states.put(v.getId(), v)));
        return states;
    }

    /**
     * Converts a single {@link Region} into an {@link OOLogic}.
     * For this to work, the region must be isolated. I. e. it does not
     * contain any {@link State}s of type {@link PseudostateKind#JOIN} nor
     * has transitions where {@link Transition#source} is of type
     * {@link PseudostateKind#FORK}.
     *
     * @param startingPoint The state from where the transformation should begin
     * @return A series of statements representing how the region would be
     * executed
     */
    private Pair<State, List<IStatement>> handleIsolatedProgression(final @NonNull State startingPoint) {

        List<IStatement> statements = new ArrayList<>();
        State activeState = startingPoint;

        while (true) {
            log.debug("Entered loop with state [{}, {}]", activeState.getId(),
                      activeState.getName());
            List<State> simple = collectSimples(activeState);
            if (simple.size() > 0) {
                statements.addAll(getStatementsFromSimpleProgression(simple));
                activeState = simple.get(simple.size() - 1).getOutgoing().get(0)
                        .getTarget();
            }
            if (activeState.getKind().isTerminating() || activeState.getKind()
                    .equals(PseudostateKind.JOIN)) {
                break;
            }
            Pair<State, List<IStatement>> nextStateAndStatement =
                    handleNonSimpleState(activeState);
            activeState = nextStateAndStatement.first();
            statements.addAll(nextStateAndStatement.second());
        }
        return new Pair<>(activeState, statements);
    }

    /**
     * @param state A state that does not have less than {@code 1} incoming
     *              or  {exactly @link code 1} outgoing {@link Transition}.
     * @return A pair where the first slot is the next state to process and
     * the second slot is the statements generated from handling the input state
     */
    private Pair<State, List<IStatement>> handleNonSimpleState(final State state) {

        if (state.getIncoming().size() > 1) {
            log.info("State [{}, {}] is the beginning of a loop", state.getId(),
                     state.getName());
            return handleLoop(state);
        }
        if (state.getOutgoing().size() > 1) {
            log.info("State [{}, {}] is branching", state.getId(),
                     state.getName());
            return handleBranching(state);
        }
        throw new IllegalArgumentException(
                String.format("State id=%s, name=%s was simple.", state.getId(),
                              state.getName()));
    }

    /**
     * @param a The state to start from
     * @param b The state to reach
     * @return {@code True} if an outgoing {@link Transition} of a has b as
     * its {@link Transition#target}.
     */
    private boolean canReach(final State a, final State b) {

        log.info("Testing reachability of [{}] from [{}]", a.getId(),
                 b.getId());
        for (Transition t : a.getOutgoing()) {
            if (t.getTarget().getId().equals(b.getId())) {
                log.info("Transition [{}] can reach startstate [{}]", t.getId(),
                         a.getId());
                return true;
            }
        }
        return false;
    }

    private Pair<State, List<IStatement>> handleLoop(final State loopStart) {

        State currentState = loopStart;
        List<IStatement> statements = new ArrayList<>();
        if (currentState.getOutgoing().size() > 1) {
            Pair<State, List<IStatement>> branch =
                    handleBranching(currentState);
            currentState = branch.first();
            statements.addAll(branch.second());
        } else {
            statements.addAll(extractStateBehavior(loopStart));
            statements.addAll(extractTransitionBehavior(
                    loopStart.getOutgoing().get(0)));
            currentState = loopStart.getOutgoing().get(0).getTarget();
        }
        while (!canReach(currentState, loopStart)) {
            List<State> simples = collectSimples(currentState);
            if (simples.size() > 0) {
                statements.addAll(getStatementsFromSimpleProgression(simples));
                currentState =
                        simples.get(simples.size() - 1).getOutgoing().get(0)
                                .getTarget();
            }
            if (canReach(currentState, loopStart)) {
                break;
            }
            Pair<State, List<IStatement>> complicated =
                    handleNonSimpleState(currentState);
            currentState = complicated.first();
            statements.addAll(complicated.second());
        }
        Transition ongoing = currentState.getOutgoing().stream()
                .filter(trans -> !trans.getTarget().getId()
                        .equals(loopStart.getId())).findFirst().get();
        List<IStatement> statementList = new ArrayList<>();
        Transition loopTransition = currentState.getOutgoing().stream()
                .filter(t -> loopStart.getIncoming().contains(t)).findFirst()
                .get();
        statementList.add(new LoopStatement(
                getConditionFromConstraint(loopTransition.getGuard()),
                statements));
        statementList.addAll(extractTransitionBehavior(ongoing));
        return new Pair<>(ongoing.getTarget(), statementList);
    }

    private String getConditionFromConstraint(final Constraint constraint) {

        if (constraint == null) {
            //TODO make default constraint customizable. True seems like a
            // decent choice as not having a guard is like the guard always
            // being fullfilled, i. e. true.
            return "true";
        }
        if (constraint.getSpecification() instanceof OpaqueExpression) {
            OpaqueExpression specification =
                    (OpaqueExpression) constraint.getSpecification();
            int index = specification.getLanguage()
                    .indexOf(Config.CONFIG.getLanguage().getName());
            if (index >= 0) {
                return specification.getBody().get(index);
            } else {
                return specification.getBody().get(0);
            }
        }
        if (constraint instanceof IntervalConstraint) {
            IntervalConstraint intervalConstraint =
                    (IntervalConstraint) constraint;
            final String condition = String.format("%s <= %s <= %s",
                                                   intervalConstraint
                                                           .getSpecification()
                                                           .getMin().toString(),
                                                   "%s", intervalConstraint
                                                           .getSpecification()
                                                           .getMax()
                                                           .toString());
            StringBuilder builder = new StringBuilder();
            intervalConstraint.getConstrainedElement().stream()
                    .filter(e -> e instanceof NamedElement).forEach(element -> {
                builder.append(String.format(condition, ((NamedElement) element)
                        .getName()));
                builder.append("&&");
            });
            builder.delete(builder.length() - 2, builder.length());
            return builder.toString();
        }
        //TODO since execution should never reach this point an exception
        // throw would be better than an empty string
        return "";
    }

    private boolean containsOneUnique(final List<State> states) {

        State current;
        State next;
        for (int i = 0; i < states.size() - 1; i++) {
            log.info(states.get(i).getId());
            current = states.get(i);
            next = states.get(i + 1);
            if (!current.getId().equals(next.getId())) {
                log.info("Ids [{}] and [{}] are not the same", current.getId(),
                         next.getId());
                return false;
            }
        }
        log.debug("Single Unique with id [{}]", states.get(0).getId());
        return true;
    }

    private Pair<State, List<IStatement>> handleBranching(final State branchPoint) {

        List<IStatement> statementList =
                new ArrayList<>(extractStateBehavior(branchPoint));
        List<State> activeStates = new ArrayList<>();
        List<IStatement>[] statements =
                new List[branchPoint.getOutgoing().size()];
        for (int i = 0; i < branchPoint.getOutgoing().size(); i++) {
            statements[i] = new ArrayList<IStatement>();
            Transition t = branchPoint.getOutgoing().get(i);
            statements[i].addAll(extractTransitionBehavior(t));
            activeStates.add(t.getTarget());
        }
        State state;
        while (!containsOneUnique(activeStates)) {
            for (int i = 0; i < activeStates.size(); i++) {
                state = activeStates.get(i);
                if (state.getKind().isTerminating() || state.getKind()
                        .equals(PseudostateKind.JUNCTION)) {
                    continue;
                }
                List<State> simples = collectSimples(state);
                if (simples.size() > 0) {
                    statements[i].addAll(getStatementsFromSimpleProgression(
                            simples));
                    state = simples.get(simples.size() - 1).getOutgoing().get(0)
                            .getTarget();
                    activeStates.set(i, state);
                }
                if (state.getKind().isTerminating() || state.getKind()
                        .equals(PseudostateKind.JUNCTION)) {
                    continue;
                }
                Pair<State, List<IStatement>> pair =
                        handleNonSimpleState(state);
                activeStates.set(i, pair.first());
                statements[i].addAll(pair.second());
            }
        }
        List<Pair<String, List<IStatement>>> branches = new ArrayList<>();
        for (int i = 0; i < statements.length; i++) {
            String condition = getConditionFromConstraint(
                    branchPoint.getOutgoing().get(i).getGuard());
            branches.add(new Pair<>(condition, statements[i]));
        }
        statementList.add(new ChoiceStatement(branches));
        statementList.addAll(extractTransitionBehavior(
                activeStates.get(0).getOutgoing().get(0)));

        return new Pair<>(activeStates.get(0).getOutgoing().get(0).getTarget(),
                          statementList);
    }

    /**
     * Transforms a list of {@link State}s into a list of
     * {@link IStatement}s. The states need to immediatly follow after each
     * other and for each state {@link State#outgoing} must have a size
     * equal to {@code 1}.
     *
     * @param states The states
     * @return The statements representing the states
     */
    private List<IStatement> getStatementsFromSimpleProgression(final @NonNull List<State> states) {

        if (!isSimpleProgression(states)) {
            throw new IllegalArgumentException(
                    "Passed argument is not a simple progression");
        }
        List<IStatement> statements = new ArrayList<>();
        for (State state : states) {
            statements.addAll(extractStateBehavior(state));
            statements.addAll(extractTransitionBehavior(
                    state.getOutgoing().get(0)));
        }
        return statements;
    }

    /**
     * Determines whether a series of {@link State}s can be deemed simple.
     * This is the case if no state has more than {@code 1} incoming
     * {@link Transition} and exactly {@code 1} incoming one. The states must
     * be ordered in their progression, i. e. the single outgoing
     * {@link Transition} of states[i] must target states[i+1] (if
     * states[i+1] exists).
     *
     * @param states The states to test
     * @return Wether the progression is deemed simple according to the
     * critera above.
     */
    private boolean isSimpleProgression(final @NonNull List<State> states) {

        State state;
        for (int i = 0; i < states.size() - 1; i++) {
            state = states.get(i);
            //A simple progression does not split into multiple paths
            if (state.getOutgoing().size() != 1) {
                return false;
            }
            //A simple progression does not combine multiple paths into a
            // single one
            if (state.getIncoming().size() > 1) {
                return false;
            }
            //A simple progression is already ordered
            if (!state.getOutgoing().get(0).getTarget()
                    .equals(states.get(i + 1))) {
                return false;
            }
        }
        if (states.size() == 0) {
            return true;
        }
        return states.get(states.size() - 1).getOutgoing().size() == 1;
    }

    /**
     * Generates a simple progression starting from the startingPoint.
     * The first {@link State} not fulfilling the condition can be reached by
     * accessing {@link State#outgoing}. That state may be a terminating state.
     *
     * @param startingPoint The state to start from
     * @return A simple progression beginning with the startingPoint
     */
    private List<State> collectSimples(final @NonNull State startingPoint) {

        List<State> progression = new ArrayList<>();
        State state = startingPoint;
        while (state.getOutgoing().size() == 1
                && state.getIncoming().size() <= 1) {
            progression.add(state);
            state = state.getOutgoing().get(0).getTarget();
        }
        return progression;
    }

}
