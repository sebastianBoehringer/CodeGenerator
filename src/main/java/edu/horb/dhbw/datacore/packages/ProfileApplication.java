package edu.horb.dhbw.datacore.packages;

import edu.horb.dhbw.datacore.commonstructure.DirectedRelationship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileApplication extends DirectedRelationship {
    private Boolean isStrict = Boolean.FALSE;
    private Profile appliedProfile;
    private UMLPackage applyingPackage;
}
