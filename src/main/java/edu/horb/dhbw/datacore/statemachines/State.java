package edu.horb.dhbw.datacore.statemachines;

import edu.horb.dhbw.datacore.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.commonbehavior.Trigger;
import edu.horb.dhbw.datacore.commonstructure.Constraint;
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

    public void setConnection(List<ConnectionPointReference> connection) {

        this.connection.addAll(connection);
    }

    public void setConnectionPoint(List<Pseudostate> connectionPoint) {

        this.connectionPoint.addAll(connectionPoint);
    }

    public void setDeferrableTrigger(List<Trigger> deferrableTrigger) {

        this.deferrableTrigger.addAll(deferrableTrigger);
    }

    public void setRegion(List<Region> region) {

        this.region.addAll(region);
    }
}
