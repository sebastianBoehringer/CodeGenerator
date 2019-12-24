package edu.horb.dhbw.datacore.uml.values;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Specifies the lack of a value.
 * <br/>
 * See subclauses 8.2 and 8.6.10 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
public final class LiteralNull extends LiteralSpecification<Void> {

    @Override
    public Void getValue() {

        return null;
    }
}
