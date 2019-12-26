package edu.horb.dhbw.datacore.uml.commonbehavior;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A special kind of behavior that does not have any sideeffect on the rest
 * of the system.
 * See subclauses 13.2 and 13.4.6 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public final class FunctionBehavior extends OpaqueBehavior {
}
