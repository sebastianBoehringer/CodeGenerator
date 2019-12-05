package edu.horb.dhbw.datacore.structuredclassifiers;

import edu.horb.dhbw.datacore.commonstructure.Dependency;
import edu.horb.dhbw.datacore.commonstructure.NamedElement;
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
public class CollaborationUse extends NamedElement {
    private List<Dependency> roleBinding = new ArrayList<>();
    //private Collaboration type;

    public void setRoleBinding(List<Dependency> roleBinding) {

        this.roleBinding.addAll(roleBinding);
    }
}
