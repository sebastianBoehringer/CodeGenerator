package edu.horb.dhbw.datacore.uml.values;

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
public class Expression extends ValueSpecification {
    private String symbol;
    private List<ValueSpecification> operand = new ArrayList<>();
}
