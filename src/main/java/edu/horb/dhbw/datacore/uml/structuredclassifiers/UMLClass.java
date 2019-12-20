package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.packages.Extension;
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
public class UMLClass extends EncapsulatedClassifier {
    private List<Property> ownedAttribute = new ArrayList<>();
    private Boolean isActive = Boolean.FALSE;
    private List<Classifier> nestedClassifier = new ArrayList<>();
    private List<Operation> ownedOperation = new ArrayList<>();
    private List<Extension> extension = new ArrayList<>();

    //TODO das das hier ne spezialisierung von BehavioredClassifier ist, fehlt
    {
        setIsAbstract(Boolean.FALSE);
    }
}
