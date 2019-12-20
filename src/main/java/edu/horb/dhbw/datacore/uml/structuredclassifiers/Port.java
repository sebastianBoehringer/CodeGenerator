package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Port extends Property {
    private Boolean isBehavior = Boolean.FALSE;
    private Boolean isConjugated = Boolean.FALSE;
    private Boolean isService = Boolean.TRUE;
    private List<Interface> required = new ArrayList<>();
    private List<Interface> provided = new ArrayList<>();
}
