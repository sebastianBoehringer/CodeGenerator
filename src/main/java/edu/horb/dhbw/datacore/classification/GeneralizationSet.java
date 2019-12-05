package edu.horb.dhbw.datacore.classification;

import edu.horb.dhbw.datacore.commonstructure.PackageableElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class GeneralizationSet extends PackageableElement {
    private Boolean isCovering = Boolean.FALSE;
    private Boolean isDisjoint = Boolean.FALSE;
    private List<Generalization> generalization = new ArrayList<>();
    private Classifier powertype;

    public void setGeneralization(List<Generalization> generalization) {

        this.generalization.addAll(generalization);
    }

}
