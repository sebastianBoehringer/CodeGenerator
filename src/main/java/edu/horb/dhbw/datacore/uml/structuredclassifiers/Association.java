package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.commonstructure.Relationship;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
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
public class Association extends Relationship {
    //TODO erbt auch von Classifier
    private Boolean isDerived = Boolean.FALSE;
    private List<Property> navigableOwnedEnd = new ArrayList<>();
    private List<Property> ownedEnd = new ArrayList<>();
    private List<Property> memberEnd = new ArrayList<>();
    private List<Type> endType = new ArrayList<>();
}
