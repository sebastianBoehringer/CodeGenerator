package edu.horb.dhbw.datacore.uml.classification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class StructuralFeature extends Feature {
    private Boolean isReadOnly = Boolean.FALSE;
    //TODO wie simulieren wir die spezialisierung von MultiplicityElement
}
