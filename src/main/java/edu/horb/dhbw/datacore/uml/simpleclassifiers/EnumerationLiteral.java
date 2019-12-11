package edu.horb.dhbw.datacore.uml.simpleclassifiers;

import edu.horb.dhbw.datacore.uml.classification.InstanceSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnumerationLiteral extends InstanceSpecification {
    /**
     * The {@link Enumeration} this {@link EnumerationLiteral} is part of
     */
    private Enumeration enumeration;
    private Enumeration classifier;
}
