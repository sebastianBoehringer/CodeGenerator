package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.enums.ParameterDirectionKind;
import edu.horb.dhbw.datacore.uml.enums.ParameterEffectKind;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectableElement;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter extends ConnectableElement {
    //TODO wieder die Spezialisierung von MultiplicityElement vs ConnectableElement/TypedElement
    private String defaults;
    private ParameterDirectionKind direction = ParameterDirectionKind.IN;
    private ParameterEffectKind effect;
    private Boolean isException = Boolean.FALSE;
    private Boolean isStream = Boolean.FALSE;
    private ValueSpecification defaultValue;

}
