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

public interface StructuredClassifier extends Classifier {
    List<Property> getOwnedAttribute();

    void setOwnedAttribute(List<Property> ownedAttribute);

    List<Connector> getOwnedConnector();

    void setOwnedConnector(List<Connector> ownedConnector);

    List<Property> getPart();

    void setPart(List<Property> part);

    List<ConnectableElement> getRole();

    void setRole(List<ConnectableElement> role);

}
