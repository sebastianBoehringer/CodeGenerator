package edu.horb.dhbw.datacore.commonbehavior;

import edu.horb.dhbw.datacore.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.structuredclassifiers.Port;
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
public class Trigger extends NamedElement {
    private Event event;
    private List<Port> port = new ArrayList<>();

    public void setPort(List<Port> port) {

        this.port.addAll(port);
    }
}
