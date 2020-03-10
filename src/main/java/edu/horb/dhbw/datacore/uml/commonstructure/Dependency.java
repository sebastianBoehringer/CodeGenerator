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

import java.util.ArrayList;
import java.util.List;

/**
 * Signifies that a model element requires others for its implementation.
 * See subclauses 7.7 and 7.8.4 of the UML specification for more details.
 * This class is supposed to be a specialization of
 * {@link DirectedRelationship} and {@link PackageableElement}. It currently
 * only inherits {@link PackageableElement}, the fields and methods from
 * {@link DirectedRelationship} and also {@link Relationship} have been
 * copied over.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dependency extends PackageableElement {
    /**
     * The elements dependent on the {@link #supplier}s.
     */
    private List<NamedElement> client;

    /**
     * The elements depending on the {@link #client}s.
     */
    private List<NamedElement> supplier;
    /**
     * The elements this relationship exists between. This can be derived.
     * Copied from {@link Relationship}.
     */
    private List<Element> relatedElement = new ArrayList<>();
    /**
     * The sources of this directed relationship.
     * This attribute can be derived.
     * Copied from {@link DirectedRelationship}.
     */
    private List<Element> source = new ArrayList<>();
    /**
     * The targets of this directed relationship.
     * This attribute can be derived.
     * Copied from {@link DirectedRelationship}.
     */
    private List<Element> target = new ArrayList<>();
}
