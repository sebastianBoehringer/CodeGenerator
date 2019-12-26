package edu.horb.dhbw.datacore.uml.values;

import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Specifies a set of values.
 * See subclauses 8 and 8.6.22 of the UML specification for more details.
 * This should specialize both
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.TypedElement} and
 * {@link PackageableElement}. It currently just inherits from
 * {@link PackageableElement}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public abstract class ValueSpecification extends PackageableElement {
    //TODO resolve inheritance issue
}
