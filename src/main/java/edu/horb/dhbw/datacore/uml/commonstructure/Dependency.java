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

package edu.horb.dhbw.datacore.uml.commonstructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Signifies that a model element requires others for its implementation.
 * See subclauses 7.7 and 7.8.4 of the UML specification for more details.
 * This class is supposed to be a specialization of
 * {@link DirectedRelationship} and {@link PackageableElement}. It currently
 * only inherits {@link PackageableElement}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dependency extends PackageableElement {
    //TODO resolve inheritance
    /**
     * The elements dependent on the {@link #supplier}s.
     */
    private List<NamedElement> client;

    /**
     * The elements depending ton the {@link #client}s.
     */
    private List<NamedElement> supplier;

    /**
     * Adds a named element to {@link #client}.
     *
     * @param element The element to add.
     */
    public void addClient(final NamedElement element) {

        client.add(element);
    }

    /**
     * Adds a named element to {@link #supplier}.
     *
     * @param element The element to add
     */
    public void addSupplier(final NamedElement element) {

        supplier.add(element);
    }

}
