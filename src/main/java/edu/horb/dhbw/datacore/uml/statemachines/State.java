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

package edu.horb.dhbw.datacore.uml.statemachines;

import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonbehavior.Trigger;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a situation that can occur during the execution of a
 * {@link StateMachine}.
 * This should specialize both {@link Vertex} and
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}. It only
 * inherits from {@link Vertex}.
 * See subclauses 14.2 and 14.5.9 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class State extends Vertex {
    //TODO resolve inheritance
    /**
     * If this is {@code true} the state is a composite state.
     * I. e. it contains at least one region. This attribute can be derived.
     */
    private Boolean isComposite;
    /**
     * If this is {@code true} the state is an orthogonal state.
     * I. e. it contains at least two regions. This attribute can be derived.
     */
    private Boolean isOrthogonal;
    /**
     * If this is {@code true} the state is simple.
     * It does not contain a region. This attribute can be derived.
     */
    private Boolean isSimple;
    /**
     * If this is {@code true} the state refers to another {@link StateMachine}.
     * This attribute can be derived.
     */
    private Boolean isSubmachineState;
    /**
     * ConnectionPointReferences for the submachine of this state.
     */
    private List<ConnectionPointReference> connection = new ArrayList<>();
    /**
     * Entry and Exit points for this state.
     * The state must be a composite one and the pseudostates in the list
     * must have a kind of
     * {@link edu.horb.dhbw.datacore.uml.enums.PseudostateKind#ENTRYPOINT} or
     * {@link edu.horb.dhbw.datacore.uml.enums.PseudostateKind#EXITPOINT}.
     */
    private List<Pseudostate> connectionPoint = new ArrayList<>();
    /**
     * Triggers that the statemachine may choose to ignore.
     * Triggers can only be ignored as long as they do not trigger
     * transitions out of the state.
     */
    private List<Trigger> deferrableTrigger = new ArrayList<>();
    /**
     * A behavior that is executed while this state is active.
     */
    private Behavior doActivity;
    /**
     * A behavior that is executed when the state is entered.
     * Any internal behavior is executed after this behavior has terminated.
     */
    private Behavior entry;
    /**
     * A behavior that is executed when the state is left.
     */
    private Behavior exit;
    /**
     * The regions contained in the state.
     */
    private List<Region> region = new ArrayList<>();
    /**
     * A constraint that holds when this state is active.
     */
    private Constraint stateInvariant;
    /**
     * The stateMachine with which this state could be replaced.
     */
    private StateMachine submachine;

    /**
     * Adds a new region to {@link #region}.
     *
     * @param newRegion The region to add
     */
    public void addRegion(final Region newRegion) {

        region.add(newRegion);
    }
}
