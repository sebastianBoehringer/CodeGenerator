package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class GeneralizationSet extends PackageableElement {
    private Boolean isCovering;
    private Boolean isDisjoint;
    private List<Generalization> generalization = new ArrayList<>();
    private Classifier powertype;

    public void setGeneralization(List<Generalization> generalization) {

        this.generalization.addAll(generalization);
    }

}
