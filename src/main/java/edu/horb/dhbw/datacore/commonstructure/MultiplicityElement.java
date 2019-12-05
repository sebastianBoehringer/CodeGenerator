package edu.horb.dhbw.datacore.commonstructure;

import edu.horb.dhbw.datacore.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.values.ValueSpecification;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class MultiplicityElement extends Element {
    private Boolean isOrdered = false;
    private Boolean isUnique = true;
    private Integer lower = 0;
    private UnlimitedNatural upper = UnlimitedNatural.UNLIMITED;
    private ValueSpecification lowerValue;
    private ValueSpecification upperValue;
}
