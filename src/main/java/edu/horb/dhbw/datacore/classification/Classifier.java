package edu.horb.dhbw.datacore.classification;

import edu.horb.dhbw.datacore.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.commonstructure.Namespace;
import edu.horb.dhbw.datacore.structuredclassifiers.CollaborationUse;
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
public abstract class Classifier extends Namespace {
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

    public void setAttribute(List<Property> attribute) {

        this.attribute.addAll(attribute);
    }

    public void setInheritedMembers(List<NamedElement> inheritedMembers) {

        this.inheritedMembers.addAll(inheritedMembers);
    }

    public void setFeature(List<Feature> feature) {

        this.feature.addAll(feature);
    }

    public void setCollaborationUse(List<CollaborationUse> collaborationUse) {

        this.collaborationUse.addAll(collaborationUse);
    }

    public void setGeneralization(List<Generalization> generalization) {

        this.generalization.addAll(generalization);
    }

    public void setSubstitution(List<Substitution> substitution) {

        this.substitution.addAll(substitution);
    }
}
