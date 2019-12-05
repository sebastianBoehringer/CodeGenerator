package edu.horb.dhbw.datacore.statemachines;

import edu.horb.dhbw.datacore.commonstructure.NamedElement;
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
public abstract class Vertex extends NamedElement {
    private Region container;
    private List<Transition> incoming = new ArrayList<>();
    private List<Transition> outgoing = new ArrayList<>();

    public void setIncoming(List<Transition> incoming) {

        this.incoming.addAll(incoming);
    }

    public void setOutgoing(List<Transition> outgoing) {

        this.outgoing.addAll(outgoing);
    }
}
