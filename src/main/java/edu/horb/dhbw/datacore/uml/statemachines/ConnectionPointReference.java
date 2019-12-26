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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to define submachine states.
 * The class is deprecated as the Codegenerator does not support submachine
 * states.
 * See subclauses 14.2 (especially 14.2.3.5) and 14.5.1 of the UML
 * specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public final class ConnectionPointReference extends Vertex {
    /**
     * The entry points this reference refers to.
     */
    private List<Pseudostate> entry = new ArrayList<>();
    /**
     * The exit points this reference refers to.
     */
    private List<Pseudostate> exit = new ArrayList<>();
    /**
     * The state defining the reference.
     */
    private State state;
}
