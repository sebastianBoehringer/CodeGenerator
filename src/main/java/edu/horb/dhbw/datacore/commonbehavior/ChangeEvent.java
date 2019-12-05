package edu.horb.dhbw.datacore.commonbehavior;

import edu.horb.dhbw.datacore.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEvent extends Event {
    private ValueSpecification changeExpression;
}
