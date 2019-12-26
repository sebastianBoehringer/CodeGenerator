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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Specifies a {@link Behavior} by the way of a finite state-machine.
 * See subclauses 14.2 and 14.5.10 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class StateMachine extends Behavior {
    /**
     * Connection points for the state machine.
     * Pseudostates in this list may only be of type
     * {@link edu.horb.dhbw.datacore.uml.enums.PseudostateKind#EXITPOINT} and
     * {@link edu.horb.dhbw.datacore.uml.enums.PseudostateKind#ENTRYPOINT}.
     */
    private List<Pseudostate> connectionPoint = new ArrayList<>();
    /**
     * The regions contained inside the stateMachine.
     */
    private List<Region> region = new ArrayList<>();
    /**
     * The states that are referring to the stateMachine as a submachine.
     */
    private List<State> submachineState = new ArrayList<>();

    /**
     * Adds a new region to {@link #region}.
     *
     * @param newRegion The region to add
     */
    public void addRegion(final Region newRegion) {

        region.add(newRegion);
    }
}
