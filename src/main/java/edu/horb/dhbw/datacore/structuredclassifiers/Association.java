package edu.horb.dhbw.datacore.structuredclassifiers;

import edu.horb.dhbw.datacore.classification.Property;
import edu.horb.dhbw.datacore.commonstructure.Relationship;
import edu.horb.dhbw.datacore.commonstructure.Type;
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

    public void setEndType(List<Type> endType) {

        this.endType.addAll(endType);
    }

    public void setNavigableOwnedEnd(List<Property> navigableOwnedEnd) {

        this.navigableOwnedEnd.addAll(navigableOwnedEnd);
    }

    public void setMemberEnd(List<Property> memberEnd) {

        this.memberEnd.addAll(memberEnd);
    }

    public void setOwnedEnd(List<Property> ownedEnd) {

        this.ownedEnd.addAll(ownedEnd);
    }
}
