package edu.horb.dhbw.datacore.classification;

import edu.horb.dhbw.datacore.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class InstanceValue extends ValueSpecification {
    private InstanceSpecification instance;
}
