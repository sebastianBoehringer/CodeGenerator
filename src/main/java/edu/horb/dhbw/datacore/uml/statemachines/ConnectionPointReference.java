package edu.horb.dhbw.datacore.uml.statemachines;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConnectionPointReference extends Vertex {
    private State state;
}
