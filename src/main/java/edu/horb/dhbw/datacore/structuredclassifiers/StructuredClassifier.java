package edu.horb.dhbw.datacore.structuredclassifiers;

import edu.horb.dhbw.datacore.classification.Classifier;
import edu.horb.dhbw.datacore.classification.Property;
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

    public void setRole(List<ConnectableElement> role) {

        this.role.addAll(role);
    }

    public void setPart(List<Property> part) {

        this.part.addAll(part);
    }

    public void setOwnedAttribute(List<Property> ownedAttribute) {

        this.ownedAttribute.addAll(ownedAttribute);
    }

    public void setOwnedConnector(List<Connector> ownedConnector) {

        this.ownedConnector.addAll(ownedConnector);
    }
}
