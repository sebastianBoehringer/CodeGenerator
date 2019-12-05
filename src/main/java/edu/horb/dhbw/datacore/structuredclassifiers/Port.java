package edu.horb.dhbw.datacore.structuredclassifiers;

import edu.horb.dhbw.datacore.classification.Property;
import edu.horb.dhbw.datacore.simpleclassifiers.Interface;
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

    public void setRequired(List<Interface> required) {

        this.required.addAll(required);
    }

    public void setProvided(List<Interface> provided) {

        this.provided.addAll(provided);
    }
}
