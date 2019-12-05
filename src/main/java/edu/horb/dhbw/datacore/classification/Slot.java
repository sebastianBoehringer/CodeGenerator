package edu.horb.dhbw.datacore.classification;

import edu.horb.dhbw.datacore.commonstructure.Element;
import edu.horb.dhbw.datacore.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slot extends Element {
    private List<ValueSpecification> value = new ArrayList<>();
    private InstanceSpecification owningInstance;
    private StructuralFeature definingFeature;

    public void setValue(List<ValueSpecification> value) {

        this.value.addAll(value);
    }
}
