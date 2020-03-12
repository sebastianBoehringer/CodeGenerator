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
import edu.horb.dhbw.datacore.uml.commonstructure.Element;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * A special case of an {@link Association} where the association itself owns
 * {@link edu.horb.dhbw.datacore.uml.classification.Property}s.
 * See subclauses 11.5 and 11.8.2 of the UML specification for more details.
 * This should specialize both {@link Association} and {@link UMLClass}. It
 * only inherits from {@link Association}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public final class AssociationClass extends UMLClassImpl
        implements Association {
    /**
     * If this is  {@code true} the association can be derived with the help
     * other elements in the model.
     * Copied from {@link AssociationImpl}.
     */
    private Boolean isDerived = Boolean.FALSE;
    /**
     * The types of the elements participating in this association. This
     * attribute can be derived.
     * Copied from {@link AssociationImpl}.
     */
    private List<Type> endType = new ArrayList<>();
    /**
     * The elements participating in this association.
     * Copied from {@link AssociationImpl}.
     */
    private List<Property> memberEnd = new ArrayList<>();
    /**
     * Navigable ends which are owned by the association.
     * Copied from {@link AssociationImpl}.
     */
    private List<Property> navigableOwnedEnd = new ArrayList<>();
    /**
     * Ends owned by the association.
     * Copied from {@link AssociationImpl}.
     */
    private List<Property> ownedEnd = new ArrayList<>();

    /**
     * The elements this relationship exists between. This can be derived.
     * Copied from {@link AssociationImpl}.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.RelationshipImpl}.
     */
    private List<Element> relatedElement = new ArrayList<>();
    //TODO write restructurer
}
