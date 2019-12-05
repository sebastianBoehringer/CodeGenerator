package edu.horb.dhbw.datacore.classification;

import edu.horb.dhbw.datacore.commonstructure.NamedElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Feature extends NamedElement {
    private Boolean isStatic = Boolean.FALSE;
    private Classifier featuringClassifier;
}
