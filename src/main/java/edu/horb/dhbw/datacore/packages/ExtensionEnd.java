package edu.horb.dhbw.datacore.packages;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import edu.horb.dhbw.datacore.classification.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtensionEnd extends Property {
    @JacksonXmlProperty(localName = "type")
    private Stereotype stereotype;
    private Integer lower; //TODO entfernen wenn irgendwo das MultiplicityElement in der
    // Vererbung wieder greift
}
