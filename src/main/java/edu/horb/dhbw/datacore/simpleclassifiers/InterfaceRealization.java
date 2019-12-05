package edu.horb.dhbw.datacore.simpleclassifiers;

import edu.horb.dhbw.datacore.commonstructure.Realization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceRealization extends Realization {
    private Interface contract;
    private BehavioredClassifier implementingClassifier;
}
