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

package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.enums.AggregationKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.DataType;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Association;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectorEnd;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the attributes of a classifier.
 * See subclauses 9.5 and 9.9.17 of the UML specification for more details.
 * This should specialize both
 * {@link edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectableElement}
 * and {@link StructuralFeature}. It currently only inherits from
 * {@link StructuralFeature}. The fields from
 * {@link edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectableElement}
 * have been copied over. This is not the most elegant solution to the issue
 * of multiple inheritance.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property extends StructuralFeature {
    //TODO resolve inheritance
    /**
     * Specifies the type of aggregation for this property.
     */
    private AggregationKind aggregation = AggregationKind.NONE;
    /**
     * A derived value. This is {@code true} if {@link #aggregation} equals
     * {@link AggregationKind#COMPOSITE}.
     */
    private Boolean isComposite = Boolean.FALSE;
    /**
     * If this is {@code true} the property can be computed via other
     * information.
     */
    private Boolean isDerived = Boolean.FALSE;
    /**
     * If this is {@code true} the property is derived by pooling together
     * all properties that subset it.
     */
    private Boolean isDerivedUnion = Boolean.FALSE;
    /**
     * If this is {@code true} this property can be used to uniquely identify
     * any instance of the classifier.
     */
    private Boolean isID = Boolean.FALSE;
    /**
     * The association this property is part of. This is optional.
     */
    private Association association;
    /**
     * The end of the association that has a qualifier. The qualifier can be
     * used to uniquely identify each instance having an association. E. g.
     * player in a team may be identified by the number on the back of his
     * shirt.
     */
    private Property associationEnd;
    /**
     * The class that this property is a part of. Only this,
     * {@link #datatype}, {@link #owningAssociation} or {@link #anInterface}
     * may be set.
     */
    private UMLClass umlClass;
    /**
     * The datatype this property is a part of. Only this, {@link #umlClass}
     * , {@link #owningAssociation}or {@link #anInterface} may be set.
     */
    private DataType datatype;
    /**
     * The default value for this property.
     */
    private ValueSpecification defaultValue;
    /**
     * The interface this property is a part of. Only this, {@link #datatype}
     * , {@link #owningAssociation} or {@link #umlClass} may be set.
     */
    private Interface anInterface;
    /**
     * If this property participates in a binary association, this is the
     * other end of said association.
     */
    private Property opposite;
    /**
     * The association owning the property. Only this, {@link #umlClass},
     * {@link #datatype} or {@link #anInterface} may be set.
     */
    private Association owningAssociation;
    /**
     * Optional list of qualifier attributes for the association end.
     */
    private List<Property> qualifier;
    /**
     * The properties this property is a subset of.
     */
    private List<Property> subsettedProperty = new ArrayList<>();

    /**
     * {@link ConnectorEnd}s attached to this connectableElement. This
     * attribute can be derived.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectableElement}.
     */
    private List<ConnectorEnd> end = new ArrayList<>();

    /**
     * Adds a new connectorEnd to {@link #end}.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectableElement}.
     *
     * @param connectorEnd The connectorEnd to add
     */
    public void addEnd(final ConnectorEnd connectorEnd) {

        end.add(connectorEnd);
    }

    /**
     * Adds a new property to {@link #qualifier}.
     *
     * @param property The property to add
     */
    public void addQualifier(final Property property) {

        qualifier.add(property);
    }

    /**
     * Adds a new property to {@link #subsettedProperty}.
     *
     * @param property The property to add
     */
    public void addSubsettedProperty(final Property property) {

        subsettedProperty.add(property);
    }
}
