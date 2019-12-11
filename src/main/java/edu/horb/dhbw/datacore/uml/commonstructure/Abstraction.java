package edu.horb.dhbw.datacore.uml.commonstructure;

import edu.horb.dhbw.datacore.uml.values.OpaqueExpression;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Abstraction {
    private OpaqueExpression mapping;
}
