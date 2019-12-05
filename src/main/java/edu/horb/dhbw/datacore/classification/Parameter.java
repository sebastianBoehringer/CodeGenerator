package edu.horb.dhbw.datacore.classification;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import edu.horb.dhbw.datacore.enums.ParameterDirectionKind;
import edu.horb.dhbw.datacore.enums.ParameterEffectKind;
import edu.horb.dhbw.datacore.structuredclassifiers.ConnectableElement;
import edu.horb.dhbw.datacore.values.ValueSpecification;
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
    private String href;
    @JacksonXmlProperty(localName = "default")
    private String defaults;
    private ParameterDirectionKind direction = ParameterDirectionKind.IN;
    private ParameterEffectKind effect;
    private Boolean isException = Boolean.FALSE;
    private Boolean isStream = Boolean.FALSE;
    private ValueSpecification defaultValue;

}
