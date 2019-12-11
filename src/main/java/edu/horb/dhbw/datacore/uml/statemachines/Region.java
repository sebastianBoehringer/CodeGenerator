package edu.horb.dhbw.datacore.uml.statemachines;

import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
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
public class Region extends Namespace {
    private State state;
    private StateMachine stateMachine;
    private List<Vertex> subvertex = new ArrayList<>();
    private List<Transition> transition = new ArrayList<>();

    public void setSubvertex(List<Vertex> subvertex) {

        this.subvertex.addAll(subvertex);
    }

    public void setTransition(List<Transition> transition) {

        this.transition.addAll(transition);
    }
}
