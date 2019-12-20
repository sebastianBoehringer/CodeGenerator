package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.CollaborationUse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Classifier extends PackageableElement {
    private Boolean isAbstract = Boolean.FALSE;
    private Boolean isFinalSpecialization = Boolean.FALSE;
    private List<CollaborationUse> collaborationUse = new ArrayList<>();
    private CollaborationUse representation;
    private List<Generalization> generalization = new ArrayList<>();
    private List<Substitution> substitution = new ArrayList<>();
    private List<Property> attribute = new ArrayList<>();
    private List<NamedElement> inheritedMembers = new ArrayList<>();
    private List<Feature> feature = new ArrayList<>();
    private GeneralizationSet powertypeExtent;


}
