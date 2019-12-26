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

package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes the collaboration of elements each performing a specialized
 * function.
 * See subclauses 11.7 and 11.8.4 of the UML specification for more details.
 * This should specialize both {@link BehavioredClassifier} and
 * {@link StructuredClassifier}. It currently only inherits from
 * {@link BehavioredClassifier}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collaboration extends BehavioredClassifier {
    //TODO resolve inheritance
    /**
     * The participants of this collaboration.
     */
    private List<ConnectableElement> collaborationRole = new ArrayList<>();

    /**
     * Adds a new connectableElement to {@link #collaborationRole}.
     *
     * @param element The connectableElement to add
     */
    public void addCollaborationRole(final ConnectableElement element) {

        collaborationRole.add(element);
    }
}
