package edu.horb.dhbw.datacore.uml.statemachines;

import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonbehavior.Trigger;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
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
public class State extends Vertex {
    //TODO erbt von Vertex und Namespace
    private Boolean isComposite;
    private Boolean isOrthogonal;
    private Boolean isSimple;
    private Boolean isSubmachineState;
    private List<ConnectionPointReference> connection = new ArrayList<>();
    private List<Pseudostate> connectionPoint = new ArrayList<>();
    private List<Trigger> deferrableTrigger = new ArrayList<>();
    private Behavior doActivity;
    private Behavior entry;
    private Behavior exit;
    private List<Region> region = new ArrayList<>();
    private Constraint stateInvariant;
    private StateMachine submachince;
}
