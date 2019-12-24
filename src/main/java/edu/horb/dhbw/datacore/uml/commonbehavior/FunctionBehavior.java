package edu.horb.dhbw.datacore.uml.commonbehavior;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A special kind of behavior that does not have any sideeffect on the rest
 * of the system.
 * <br/>
 * See subclauses 13.2 and 13.4.6 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class FunctionBehavior extends OpaqueBehavior {
}
