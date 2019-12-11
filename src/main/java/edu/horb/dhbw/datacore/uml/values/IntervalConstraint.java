package edu.horb.dhbw.datacore.uml.values;

import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntervalConstraint extends Constraint {
    private Interval specification;
}
