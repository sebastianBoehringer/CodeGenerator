package edu.horb.dhbw.datacore.simpleclassifiers;

import edu.horb.dhbw.datacore.classification.Classifier;
import edu.horb.dhbw.datacore.classification.Operation;
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
public class DataType extends Classifier {
    private List<Property> ownedAttribute = new ArrayList<>();
    private List<Operation> ownedOperation = new ArrayList<>();

    public void setOwnedAttribute(List<Property> ownedAttribute) {

        this.ownedAttribute.addAll(ownedAttribute);
    }

    public void setOwnedOperation(List<Operation> ownedOperation) {

        this.ownedOperation.addAll(ownedOperation);
    }
}
