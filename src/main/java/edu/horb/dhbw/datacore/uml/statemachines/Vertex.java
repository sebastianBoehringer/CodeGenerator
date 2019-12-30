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

import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * A vertex represents a target for a {@link Transition}.
 * It can be seen as a node of a stateMachine.
 * See subclauses 14.2 and 14.5.13 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Vertex extends NamedElement {
    /**
     * The region containing the vertex.
     */
    private Region container;
    /**
     * All transitions that enter the vertex.
     * I. e. all transitions where {@link Transition#target} equals this
     * vertex. Thus this attribute can be derived.
     */
    private List<Transition> incoming = new ArrayList<>();
    /**
     * All transitions that leave the vertex.
     * I. e. all transitions where {@link Transition#source} equals this
     * vertex. Thus the attribute can be derived.
     */
    private List<Transition> outgoing = new ArrayList<>();

    /**
     * Adds a new transition to {@link #incoming}.
     *
     * @param transition The transition to add
     */
    public final void addIncoming(final Transition transition) {

        incoming.add(transition);
    }

    /**
     * Adds a new transition to {@link #outgoing}.
     *
     * @param transition The transition to add
     */
    public final void addOutgoing(final Transition transition) {

        outgoing.add(transition);
    }
}
