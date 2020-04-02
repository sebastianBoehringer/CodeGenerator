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

package edu.horb.dhbw.datacore.uml.enums;

import lombok.NonNull;

/**
 * Used to differentiate between
 * {@link edu.horb.dhbw.datacore.uml.statemachines.State}s.
 * See subclauses 14.2 and 14.5.7 of the UML specification for more details.
 * Another literal - namely {@link #STATE} has been added to render
 * certain classes of the uml specification, i. e. Pseudostate and Vertex
 * redundant.
 */
public enum PseudostateKind {
    /**
     * A starting point for a
     * {@link edu.horb.dhbw.datacore.uml.statemachines.Region}, i. e. the
     * execution of the region begins in this pseudostate.
     */
    INITIAL,
    /**
     * A pseudostate saving the most recent state configuration, i. e. which
     * states have been active (in execution). Entering a
     * {@link edu.horb.dhbw.datacore.uml.statemachines.State} of this
     * kind will reset the region to that state configuration. Deprecated as
     * the Codegenerator does not support the feature.
     */
    @Deprecated DEEPHISTORY,
    /**
     * Represents the most recent active state of the region, not the entire
     * state configuration. Entering this kind of pseudostate will reset the
     * the entire containing region and begin by entering the active state
     * again. Deprecated as the Codegenerator does not support the feature.
     */
    @Deprecated SHALLOWHISTORY,

    /**
     * Used to synchronize multiple incoming
     * {@link edu.horb.dhbw.datacore.uml.statemachines.Transition}s. Incoming
     * {@link edu.horb.dhbw.datacore.uml.statemachines.Transition}s may not
     * have a guard.
     * The {@link edu.horb.dhbw.datacore.uml.statemachines.Transition#source} of
     * the incoming
     * {@link edu.horb.dhbw.datacore.uml.statemachines.Transition}s must be
     * contained in different orthogonal
     * {@link edu.horb.dhbw.datacore.uml.statemachines.Region} of a
     * composite {@link edu.horb.dhbw.datacore.uml.statemachines.State}.
     * Since the codegenerator does not support composite states it does not
     * support JOIN pseudoStates either.
     */
    @Deprecated JOIN,
    /**
     * Used to start parallel execution. Outgoing
     * {@link edu.horb.dhbw.datacore.uml.statemachines.Transition}s may not
     * have a guard.
     * The {@link edu.horb.dhbw.datacore.uml.statemachines.Transition#target} of
     * the incoming
     * {@link edu.horb.dhbw.datacore.uml.statemachines.Transition}s must be
     * contained in different orthogonal
     * {@link edu.horb.dhbw.datacore.uml.statemachines.Region} of a
     * composite {@link edu.horb.dhbw.datacore.uml.statemachines.State}.
     * Since the codegenerator does not support composite states it does not
     * support FORK pseudoStates either.
     */
    @Deprecated FORK,
    /**
     * A kind of pseudostate that can be used to multiply or reduce the
     * number of incoming/ outgoing
     * {@link edu.horb.dhbw.datacore.uml.statemachines.Transition}s. Compared
     * to {@link #FORK} and {@link #JOIN} this does not add meaning about
     * synchronization or parallelism.
     */
    JUNCTION,
    /**
     * Used to represent a conditional branch. Only one of its outgoing
     * {@link edu.horb.dhbw.datacore.uml.statemachines.Transition}s may be
     * executed.
     */
    CHOICE,
    /**
     * Represents a way to start the execution in a state different to an
     * {@link #INITIAL} pseudostate.
     */
    @Deprecated ENTRYPOINT,
    /**
     * A way to exit a composite or submachine state.
     */
    @Deprecated EXITPOINT,
    /**
     * Terminates the execution of the entire
     * {@link edu.horb.dhbw.datacore.uml.statemachines.StateMachine}.
     */
    TERMINATE,
    /**
     * If a {@link edu.horb.dhbw.datacore.uml.statemachines.State} of this
     * kind is reached, the execution of the
     * {@link edu.horb.dhbw.datacore.uml.statemachines.StateMachine} completes.
     */
    FINAL,
    /**
     * A normal {@link edu.horb.dhbw.datacore.uml.statemachines.State}, i. e.
     * a state that is acutally not a PseudoState.
     */
    STATE;

    /**
     * Case insensitive wrapper around {@link #valueOf(String)}.
     *
     * @param string The string identifying an enum constant
     * @return The enum constant identified by the string
     */
    public static PseudostateKind from(@NonNull final String string) {

        return PseudostateKind.valueOf(string.toUpperCase());
    }

    /**
     * @return {@code True} if reaching a
     * {@link edu.horb.dhbw.datacore.uml.statemachines.State} of this kind
     * terminates the
     * {@link edu.horb.dhbw.datacore.uml.statemachines.StateMachine}
     * containing the {@link edu.horb.dhbw.datacore.uml.statemachines.State}.
     */
    public boolean isTerminating() {

        return this.equals(FINAL) || this.equals(TERMINATE);
    }

    /**
     * @return {@code True} if this state is used as a means of
     * synchronization. I. e. it has a
     * {@link edu.horb.dhbw.datacore.uml.statemachines.State#kind} of
     * {@link #JOIN} or {@link #FORK}.
     */
    public boolean isSynchronizationPseudoState() {

        return this.equals(JOIN) || this.equals(FORK);
    }

    /**
     * Checks whether the literal denotes a pseudoState.
     * Every literal denotes a pseudoState besides {@link #FINAL} and
     * {@link #STATE}.
     *
     * @return {@code true} if the kind signifies a pseudostate, {@code false
     * } otherwise
     */
    public boolean isPseudoState() {

        return !this.equals(STATE) && !this.equals(FINAL);
    }
}
