package edu.horb.dhbw.datacore.classification;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import edu.horb.dhbw.datacore.enums.AggregationKind;
import edu.horb.dhbw.datacore.simpleclassifiers.DataType;
import edu.horb.dhbw.datacore.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.structuredclassifiers.Association;
import edu.horb.dhbw.datacore.structuredclassifiers.UMLClass;
import edu.horb.dhbw.datacore.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property extends StructuralFeature {
    private AggregationKind aggregation = AggregationKind.NONE;
    private Boolean isComposite = Boolean.FALSE;
    private Boolean isDerived = Boolean.FALSE;
    private Boolean isDerivedUnion = Boolean.FALSE;
    private Boolean isID = Boolean.FALSE;
    private ValueSpecification defaultValue;
    private List<Property> qualifier;
    private Property associationEnd;
    private Association owningAssociation;
    private Association association;
    @JacksonXmlProperty(localName = "interface")
    private Interface anInterface;
    private DataType datatype;
    @JacksonXmlProperty(localName = "class")
    private UMLClass umlClass;

    public void setQualifier(List<Property> qualifier) {

        this.qualifier.addAll(qualifier);
    }
}
