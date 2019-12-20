package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.commonstructure.Dependency;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollaborationUse extends NamedElement {
    private List<Dependency> roleBinding = new ArrayList<>();
    @Getter(AccessLevel.NONE)
    private Collaboration type;
    //TODO issue with attribute of same name
}
