package edu.horb.dhbw.datacore.uml.packages;

import edu.horb.dhbw.datacore.uml.structuredclassifiers.Association;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Extension extends Association {
    private Boolean isRequired = Boolean.FALSE;
    private UMLClass metaclass;

}
