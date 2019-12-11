package edu.horb.dhbw.datacore.uml.packages;

import edu.horb.dhbw.datacore.uml.commonstructure.DirectedRelationship;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PackageMerge extends DirectedRelationship {
    private UMLPackage mergedPackage;
    private UMLPackage receivingPackage;
}
