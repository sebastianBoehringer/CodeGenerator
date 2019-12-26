package edu.horb.dhbw.datacore.uml.simpleclassifiers;

import edu.horb.dhbw.datacore.uml.commonstructure.Realization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Indicates a relationship where a classifier implements an interface.
 * See subclauses 10.4 and 10.5.6 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceRealization extends Realization {
    /**
     * The interface realized by {@link #implementingClassifier}.
     */
    private Interface contract;
    /**
     * The classifier realizing the {@link #contract}.
     */
    private BehavioredClassifier implementingClassifier;
}
