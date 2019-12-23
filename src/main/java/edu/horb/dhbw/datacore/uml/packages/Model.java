package edu.horb.dhbw.datacore.uml.packages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents an abstracted view on a system.
 * <br/>
 * See subclauses 12.2 and 12.4.4 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Model extends UMLPackage {
    /**
     * The viewpoint expressed by the model.
     */
    private String viewpoint;
}
