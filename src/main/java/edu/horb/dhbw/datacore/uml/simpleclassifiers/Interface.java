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

package edu.horb.dhbw.datacore.uml.simpleclassifiers;

import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a contract that a {@link BehavioredClassifier} can implement via
 * an {@link InterfaceRealization}.
 * See subclauses 10.4 and 10.5.5 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Interface extends Classifier {
    /**
     * Classifiers defined inside of this Interface.
     */
    private List<Classifier> nestedClassifier = new ArrayList<>();
    /**
     * Attributes owned by the interface. Realizing classifiers do not have
     * to own the same attributes. They may also be implemented by behaviors.
     */
    private List<Property> ownedAttribute = new ArrayList<>();
    /**
     * Operations that belong to this interface.
     */
    private List<Operation> ownedOperation = new ArrayList<>();
    /**
     * Adds a new property to {@link #ownedAttribute}.
     *
     * @param property The property to add
     */
    public void addOwnedAttribute(final Property property) {

        ownedAttribute.add(property);
    }

    /**
     * Adds a new classifier to {@link #nestedClassifier}.
     *
     * @param classifier The classifier to add
     */
    public void addNestedClassifier(final Classifier classifier) {

        nestedClassifier.add(classifier);
    }

    /**
     * Adds a new operation to {@link #ownedOperation}.
     *
     * @param operation The operation to add
     */
    public void addOperation(final Operation operation) {

        ownedOperation.add(operation);
    }
}
