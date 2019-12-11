package edu.horb.dhbw.datacore.uml.commonstructure;

import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElementImport extends DirectedRelationship {
    private String alias;
    private VisibilityKind visibility;
    private Namespace importingNamespace;
    private PackageableElement importedElement;
}
