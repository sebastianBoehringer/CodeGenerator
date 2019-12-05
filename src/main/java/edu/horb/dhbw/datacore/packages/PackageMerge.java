package edu.horb.dhbw.datacore.packages;

import edu.horb.dhbw.datacore.commonstructure.DirectedRelationship;
import lombok.Data;

@Data
public class PackageMerge extends DirectedRelationship {
    private UMLPackage mergedPackage;
    private UMLPackage receivingPackage;
}
