package edu.horb.dhbw.datacore.statemachines;

import edu.horb.dhbw.datacore.enums.PseudostateKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pseudostate extends Vertex {
    private PseudostateKind kind = PseudostateKind.INITIAL;
    private State state;
    private StateMachine stateMachine;
}
