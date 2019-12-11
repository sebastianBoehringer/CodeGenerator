package edu.horb.dhbw.datacore.uml.commonstructure;

import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageImport extends DirectedRelationship {
    private VisibilityKind visibility = VisibilityKind.PUBLIC;
    private UMLPackage importedPackage;
    private Namespace importingNamespace;

}

