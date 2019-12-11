package edu.horb.dhbw.datacore.uml.packages;

import edu.horb.dhbw.datacore.uml.classification.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtensionEnd extends Property {
    private Stereotype stereotype;
    private Integer lower; //TODO entfernen wenn irgendwo das MultiplicityElement in der
    // Vererbung wieder greift
}
