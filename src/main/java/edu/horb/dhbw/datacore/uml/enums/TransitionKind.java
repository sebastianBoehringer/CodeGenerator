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

/**
 * Differentiates kinds of transitions.
 * See subclauses 14.2 and 14.5.12 of the UML specification for more details.
 */
public enum TransitionKind {
    /**
     * A transition where the source and target are the same state.
     * Neither the exit nor the entry behavior of the state will be executed
     * when this kind of transition is taken. The source state must be a
     * {@link edu.horb.dhbw.datacore.uml.statemachines.State} not a
     * {@link edu.horb.dhbw.datacore.uml.statemachines.Pseudostate}.
     */
    INTERNAL,
    /**
     * A transition that does not leave a composite state.
     * This kind of transition exits a state contained in a composite one and
     * enters a new one inside of that composite state. It does not invoke
     * the exit behavior of the composite state.
     */
    LOCAL,
    /**
     * This kind of transition exits its source state.
     */
    EXTERNAL
}
