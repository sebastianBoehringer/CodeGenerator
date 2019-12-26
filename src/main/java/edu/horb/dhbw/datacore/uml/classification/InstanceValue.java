package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Identifies a certain instance.
 * See subclauses 9.8 and 9.9.10 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public final class InstanceValue extends ValueSpecification {
    /**
     * Represents the specified value.
     */
    private InstanceSpecification instance;
}
