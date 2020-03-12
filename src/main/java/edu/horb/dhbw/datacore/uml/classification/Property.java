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

package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.enums.AggregationKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.DataType;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Association;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectableElement;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;

import java.util.List;

public interface Property extends StructuralFeature, ConnectableElement {
    AggregationKind getAggregation();

    void setAggregation(AggregationKind aggregation);

    Boolean getIsComposite();

    void setIsComposite(Boolean isComposite);

    Boolean getIsDerived();

    void setIsDerived(Boolean isDerived);

    Boolean getIsDerivedUnion();

    void setIsDerivedUnion(Boolean isDerivedUnion);

    Boolean getIsID();

    void setIsID(Boolean isID);

    Association getAssociation();

    void setAssociation(Association association);

    Property getAssociationEnd();

    void setAssociationEnd(Property associationEnd);

    UMLClass getUmlClass();

    void setUmlClass(UMLClass umlClass);

    DataType getDatatype();

    void setDatatype(DataType datatype);

    ValueSpecification getDefaultValue();

    void setDefaultValue(ValueSpecification defaultValue);

    Interface getAnInterface();

    void setAnInterface(Interface anInterface);

    Property getOpposite();

    void setOpposite(Property opposite);

    Association getOwningAssociation();

    void setOwningAssociation(Association owningAssociation);

    List<Property> getQualifier();

    void setQualifier(List<Property> qualifier);

    List<Property> getSubsettedProperty();

    void setSubsettedProperty(List<Property> subsettedProperty);
}
