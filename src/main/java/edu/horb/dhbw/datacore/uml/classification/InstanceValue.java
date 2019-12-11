package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class InstanceValue extends ValueSpecification {
    private InstanceSpecification instance;
}
