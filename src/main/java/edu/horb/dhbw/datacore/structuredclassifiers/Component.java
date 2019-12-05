package edu.horb.dhbw.datacore.structuredclassifiers;

import edu.horb.dhbw.datacore.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.simpleclassifiers.Interface;
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
public class Component extends UMLClass {
    private Boolean isIndirectlyInstantiated = Boolean.TRUE;
    private List<PackageableElement> packagedElement = new ArrayList<>();
    private List<ComponentRealization> realization = new ArrayList<>();
    private List<Interface> provided = new ArrayList<>();
    private List<Interface> required = new ArrayList<>();

    public void setProvided(List<Interface> provided) {

        this.provided.addAll(provided);
    }

    public void setRequired(List<Interface> required) {

        this.required.addAll(required);
    }

    public void setPackagedElement(List<PackageableElement> packagedElement) {

        this.packagedElement.addAll(packagedElement);
    }

    public void setRealization(List<ComponentRealization> realization) {

        this.realization.addAll(realization);
    }
}
