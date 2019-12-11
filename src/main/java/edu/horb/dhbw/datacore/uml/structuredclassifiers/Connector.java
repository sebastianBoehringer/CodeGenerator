package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.classification.Feature;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.enums.ConnectorKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Connector extends Feature {
    private ConnectorKind kind;
    private List<ConnectorEnd> end = new ArrayList<>();
    //private Association type;
    private Behavior contract;

    public void setEnd(List<ConnectorEnd> end) {

        this.end.addAll(end);
    }
}
