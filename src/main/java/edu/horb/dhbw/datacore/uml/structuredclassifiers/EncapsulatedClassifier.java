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

import java.util.List;

/**
 * A classifier that can own ports.
 * See subclauses 11.3 and 11.8.13 of the UML specification for more details.
 */
public interface EncapsulatedClassifier extends StructuredClassifier {
    /**
     * Returns the ports owned by the classifier.
     * Since this attribute can be derived an implementation does not need to
     * store a field.
     *
     * @return The ports owned by the classifier.
     */
    List<Port> getOwnedPort();

    /**
     * @param ownedPort The ports owned by the classifier.
     */
    void setOwnedPort(List<Port> ownedPort);
}
