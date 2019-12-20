package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
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


}
