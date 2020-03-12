/*
 * Copyright (c) 2020 Sebastian Boehringer.
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

import java.util.List;

/**
 * A classifier that can have an internal structure.
 * See subclauses 11.2 and 11.8.15 of the UML specification for more details.
 */
public interface StructuredClassifier extends Classifier {
    /**
     * @return The attributes owned by the classifier.
     */
    List<Property> getOwnedAttribute();

    /**
     * @param ownedAttribute Attributes owned by this classifier
     */
    void setOwnedAttribute(List<Property> ownedAttribute);

    /**
     * @return The connectors connected to this classifier.
     */
    List<Connector> getOwnedConnector();

    /**
     * @param ownedConnector Connectors connected to this classifier.
     */
    void setOwnedConnector(List<Connector> ownedConnector);

    /**
     * @return Instances owned by the classifier via composition, i. e.
     * properties where {@link Property#isComposite} is {@code true}.
     */
    List<Property> getPart();

    /**
     * @param part Properties this classifier owns where
     *             {@link Property#isComposite} is {@code true}.
     */
    void setPart(List<Property> part);

    /**
     * @return Roles that instances can play in the classifier.
     */
    List<ConnectableElement> getRole();

    /**
     * @param role The roles that instances can play in the classifier.
     */
    void setRole(List<ConnectableElement> role);

}
