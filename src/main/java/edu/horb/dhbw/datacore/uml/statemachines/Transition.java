package edu.horb.dhbw.datacore.uml.statemachines;

import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonbehavior.Trigger;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.enums.TransitionKind;
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
public class Transition extends Namespace {
    private TransitionKind kind = TransitionKind.EXTERNAL;
    private Region container;
    private Behavior effect;
    private Constraint guard;
    private Vertex source;
    private Vertex target;
    private List<Trigger> trigger = new ArrayList<>();

    public void setTrigger(List<Trigger> trigger) {

        this.trigger.addAll(trigger);
    }
}
