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

import edu.horb.dhbw.datacore.uml.enums.PseudostateKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A special type of {@link Vertex} in which the execution of the
 * statemachine never rests.
 * See subclauses 14.2 and 14.5.6 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Pseudostate extends Vertex {
    /**
     * The type of the Pseudostate. See {@link PseudostateKind} for all
     * possible kinds of Pseudostates.
     */
    private PseudostateKind kind = PseudostateKind.INITIAL;
    /**
     * The state this pseudostate appears in, if any.
     */
    private State state;
    /**
     * The Statemachine the pseudostate is defined in. Only applies to
     * pseudostates with {@link #kind} of {@link PseudostateKind#ENTRYPOINT}
     * and {@link PseudostateKind#EXITPOINT}.
     */
    private StateMachine stateMachine;
}
