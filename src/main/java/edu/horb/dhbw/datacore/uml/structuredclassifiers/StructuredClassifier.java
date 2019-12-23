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

import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * A classifier that can have an internal structure.
 * <br/>
 * See subclauses 11.2 and 11.8.15 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class StructuredClassifier extends Classifier {
    /**
     * The attributes owned by the classifier.
     */
    private List<Property> ownedAttribute = new ArrayList<>();
    /**
     * The connectors connected to this classifier.
     */
    private List<Connector> ownedConnector = new ArrayList<>();
    /**
     * Roles that instances can play in the classifier.
     */
    private List<ConnectableElement> role = new ArrayList<>();
    /**
     * Instances owned by the classifier via composition, i. e.  properties
     * where {@link Property#getIsComposite()} is {@code true}.
     */
    private List<Property> part = new ArrayList<>();

    /**
     * Adds a new connectableElement to {@link #role}.
     *
     * @param element The element to add
     */
    public void addRole(final ConnectableElement element) {

        role.add(element);
    }

    /**
     * Adds a new property to {@link #part}.
     *
     * @param property The property to add
     */
    public void addPart(final Property property) {

        part.add(property);
    }

    /**
     * Adds a new connector to {@link #ownedConnector}.
     *
     * @param connector The connector to add
     */
    public void addOwnedConnector(final Connector connector) {

        ownedConnector.add(connector);
    }

    /**
     * Adds a new property to {@link #ownedAttribute}.
     *
     * @param property The property to add
     */
    public void addOwnedAttribute(final Property property) {

        ownedAttribute.add(property);
    }
}
