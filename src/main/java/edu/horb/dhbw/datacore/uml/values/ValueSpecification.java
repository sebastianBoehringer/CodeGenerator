package edu.horb.dhbw.datacore.uml.values;

import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Specifies a set of values.
 * See subclauses 8 and 8.6.22 of the UML specification for more details.
 * This should specialize both
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.TypedElement} and
 * {@link PackageableElement}. It currently just inherits from
 * {@link PackageableElement}, the field from
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.TypedElement} has been
 * copied over.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ValueSpecification extends PackageableElement {
    /**
     * The type of this element.
     * Copied over from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.TypedElement}.
     */
    private Type type;
}
