package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElementImpl;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Identifies a certain instance.
 * See subclauses 9.8 and 9.9.10 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class InstanceValue extends PackageableElementImpl
        implements ValueSpecification {
    /**
     * Represents the specified value.
     */
    private InstanceSpecification instance;

    private Type type;
}
