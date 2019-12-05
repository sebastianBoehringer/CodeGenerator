package edu.horb.dhbw.datacore.classification;

import edu.horb.dhbw.datacore.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.values.ValueSpecification;
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
public class InstanceSpecification extends PackageableElement {
    private List<Slot> slot = new ArrayList<>();
    private ValueSpecification specification;
    private Classifier classifier;

    public void setSlot(List<Slot> slot) {

        this.slot.addAll(slot);
    }
}
