package edu.horb.dhbw.datacore.commonstructure;

import edu.horb.dhbw.datacore.enums.VisibilityKind;
import edu.horb.dhbw.datacore.packages.UMLPackage;
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

