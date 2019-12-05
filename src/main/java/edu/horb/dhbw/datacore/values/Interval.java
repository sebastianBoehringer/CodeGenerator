package edu.horb.dhbw.datacore.values;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interval extends ValueSpecification {
    private ValueSpecification max;
    private ValueSpecification min;
}
