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
     */
    JOIN,
    /**
     * Used to start parallel execution. Outgoing
     * {@link edu.horb.dhbw.datacore.uml.statemachines.Transition}s may not
     * have a guard.
     */
    FORK,
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
    ENTRYPOINT,
    /**
     * A way to exit a composite or submachine state.
     */
    EXITPOINT,
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
}
