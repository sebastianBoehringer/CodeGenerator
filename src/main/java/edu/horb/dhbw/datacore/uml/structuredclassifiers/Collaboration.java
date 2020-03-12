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

import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifierImpl;
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
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Collaboration extends BehavioredClassifierImpl
        implements StructuredClassifier {
    /**
     * The participants of this collaboration.
     */
    private List<ConnectableElement> collaborationRole = new ArrayList<>();
    /**
     * The attributes owned by the classifier.
     * Copied from {@link StructuredClassifier}.
     */
    private List<Property> ownedAttribute = new ArrayList<>();
    /**
     * The connectors connected to this classifier.
     * Copied from {@link StructuredClassifier}.
     */
    private List<Connector> ownedConnector = new ArrayList<>();
    /**
     * Instances owned by the classifier via composition, i. e.  properties
     * where {@link Property#isComposite} is {@code true}.
     * Copied from {@link StructuredClassifier}.
     */
    private List<Property> part = new ArrayList<>();
    /**
     * Roles that instances can play in the classifier.
     * Copied from {@link StructuredClassifier}.
     */
    private List<ConnectableElement> role = new ArrayList<>();
    //TODO write restructurer
}
