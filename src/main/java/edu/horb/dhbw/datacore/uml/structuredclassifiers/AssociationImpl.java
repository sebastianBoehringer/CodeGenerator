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

import edu.horb.dhbw.datacore.uml.classification.ClassifierImpl;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.commonstructure.Element;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * A way to classify links which are tuples referring to
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.TypedElement}s.
 * See subclauses 11.5 and 11.8.1 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociationImpl extends ClassifierImpl implements Association {
    /**
     * If this is  {@code true} the association can be derived with the help
     * other elements in the model.
     */
    private Boolean isDerived = Boolean.FALSE;
    /**
     * The types of the elements participating in this association. This
     * attribute can be derived.
     */
    private List<Type> endType = new ArrayList<>();
    /**
     * The elements participating in this association.
     */
    private List<Property> memberEnd = new ArrayList<>();
    /**
     * Navigable ends which are owned by the association.
     */
    private List<Property> navigableOwnedEnd = new ArrayList<>();
    /**
     * Ends owned by the association.
     */
    private List<Property> ownedEnd = new ArrayList<>();

    /**
     * The elements this relationship exists between. This can be derived.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.RelationshipImpl}.
     */
    private List<Element> relatedElement = new ArrayList<>();
    //TODO write restructurer
}
