package edu.horb.dhbw.datacore.structuredclassifiers;

import edu.horb.dhbw.datacore.classification.Feature;
import edu.horb.dhbw.datacore.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.enums.ConnectorKind;
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
