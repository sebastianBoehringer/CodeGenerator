package edu.horb.dhbw.datacore.statemachines;

import edu.horb.dhbw.datacore.commonbehavior.Behavior;
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
public class StateMachine extends Behavior {
    private List<Pseudostate> connectionPoint = new ArrayList<>();
    private List<Region> region = new ArrayList<>();
    private State submachineState;

    public void setConnectionPoint(List<Pseudostate> connectionPoint) {

        this.connectionPoint.addAll(connectionPoint);
    }

    public void setRegion(List<Region> region) {

        this.region.addAll(region);
    }
}
