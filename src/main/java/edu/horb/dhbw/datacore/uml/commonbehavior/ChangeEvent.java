package edu.horb.dhbw.datacore.uml.commonbehavior;

import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
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
