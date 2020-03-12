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

import edu.horb.dhbw.datacore.uml.commonstructure.NamespaceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * A container for states and vertices inside a stateMachine or composite state.
 * They may be used to represent parallel execution.
 * See subclauses 14.2 and 14.5.8 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Region extends NamespaceImpl {
    /**
     * The state owning the region.
     * Only this or {@link #stateMachine} may be set.
     */
    private State state;
    /**
     * The stateMachine owning the region.
     * Only this or {@link #state} may be set.
     */
    private StateMachine stateMachine;
    /**
     * Vertices inside the region.
     */
    private List<State> subvertex = new ArrayList<>();
    /**
     * Transitions inside the region.
     */
    private List<Transition> transition = new ArrayList<>();
}
