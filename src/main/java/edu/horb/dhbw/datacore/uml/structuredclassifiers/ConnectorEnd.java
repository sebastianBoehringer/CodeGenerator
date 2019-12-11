package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectorEnd extends MultiplicityElement {
    private ConnectableElement role;
    private Property definingEnd;
    private Property partWithPort;
}
