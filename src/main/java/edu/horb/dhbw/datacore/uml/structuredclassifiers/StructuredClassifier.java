package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.Property;
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
public abstract class StructuredClassifier extends Classifier {
    private List<Property> ownedAttribute = new ArrayList<>();
    private List<Connector> ownedConnector = new ArrayList<>();
    private List<ConnectableElement> role = new ArrayList<>();
    private List<Property> part = new ArrayList<>();
}
