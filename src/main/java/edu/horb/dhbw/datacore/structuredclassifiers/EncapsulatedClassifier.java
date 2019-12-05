package edu.horb.dhbw.datacore.structuredclassifiers;

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
public class EncapsulatedClassifier extends StructuredClassifier {
    private List<Port> ownedPort = new ArrayList<>();

    public void setOwnedPort(List<Port> ownedPort) {

        this.ownedPort.addAll(ownedPort);
    }
}
