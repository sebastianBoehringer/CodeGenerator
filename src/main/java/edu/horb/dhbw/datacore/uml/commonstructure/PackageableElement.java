package edu.horb.dhbw.datacore.uml.commonstructure;

import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public abstract class PackageableElement extends NamedElement {

    {
        setVisbility(VisibilityKind.PUBLIC);
    }
}
