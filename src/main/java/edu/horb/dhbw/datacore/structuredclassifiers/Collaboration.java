package edu.horb.dhbw.datacore.structuredclassifiers;

import edu.horb.dhbw.datacore.simpleclassifiers.BehavioredClassifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collaboration extends BehavioredClassifier {
    //TODO erbt theoretisch auch von StructuredClassifier
    private ConnectableElement collaborationRole;
}
