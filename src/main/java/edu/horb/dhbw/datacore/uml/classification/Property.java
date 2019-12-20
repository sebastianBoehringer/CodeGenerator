package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.enums.AggregationKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.DataType;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Association;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property extends StructuralFeature {
    @Getter(AccessLevel.NONE)
    public Type type;
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
    private Interface anInterface;
    private DataType datatype;
    private UMLClass umlClass;



}
