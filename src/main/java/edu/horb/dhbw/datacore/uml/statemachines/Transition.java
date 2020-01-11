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
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.enums.TransitionKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Connects two vertices.
 * See subclauses 14.2 and 14.5.11 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Transition extends Namespace {
    /**
     * The type of the transition.
     */
    private TransitionKind kind = TransitionKind.EXTERNAL;
    /**
     * The region that owns the transition.
     */
    private Region container;
    /**
     * An optional behavior that is executed when the transition is taken.
     */
    private Behavior effect;
    /**
     * The transition can only be taken when this constraint holds.
     * If no guard is specified the transition can always be taken.
     */
    private Constraint guard;
    /**
     * The vertex the transition is coming from.
     */
    private Vertex source;
    /**
     * The vertex the transition is going to.
     */
    private Vertex target;
}
