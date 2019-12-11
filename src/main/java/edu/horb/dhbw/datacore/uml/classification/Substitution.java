package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.commonstructure.Realization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Substitution extends Realization {
    private Classifier contract;
    private Classifier substitutingClassifier;
}
