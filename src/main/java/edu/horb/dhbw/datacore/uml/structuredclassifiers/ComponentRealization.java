package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.commonstructure.Realization;
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
public class ComponentRealization extends Realization {
    private Component abstraction;
    private List<Classifier> realizingClassifier = new ArrayList<>();

    public void setRealizingClassifier(List<Classifier> realizingClassifier) {

        this.realizingClassifier.addAll(realizingClassifier);
    }
}
