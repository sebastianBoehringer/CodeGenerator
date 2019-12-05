package edu.horb.dhbw.datacore.structuredclassifiers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import edu.horb.dhbw.config.jackson.typeresolvers.UMLClassIdResolver;
import edu.horb.dhbw.datacore.classification.Classifier;
import edu.horb.dhbw.datacore.classification.Operation;
import edu.horb.dhbw.datacore.classification.Property;
import edu.horb.dhbw.datacore.packages.Extension;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeIdResolver(UMLClassIdResolver.class)
public class UMLClass extends EncapsulatedClassifier {
    private Boolean isActive = Boolean.FALSE;
    private List<Classifier> nestedClassifier = new ArrayList<>();
    private List<Property> ownedAttribute = new ArrayList<>();
    private List<Operation> ownedOperation = new ArrayList<>();
    private List<Extension> extension = new ArrayList<>();

    //TODO das das hier ne spezialisierung von BehavioredClassifier ist, fehlt
    {
        setIsAbstract(Boolean.FALSE);
    }

    public void setExtension(List<Extension> extension) {

        this.extension.addAll(extension);
    }

    public void setNestedClassifier(List<Classifier> nestedClassifier) {

        this.nestedClassifier.addAll(nestedClassifier);
    }

    public void setOwnedAttribute(List<Property> ownedAttribute) {

        this.ownedAttribute.addAll(ownedAttribute);
    }

    public void setOwnedOperation(List<Operation> ownedOperation) {

        this.ownedOperation.addAll(ownedOperation);
    }
}
