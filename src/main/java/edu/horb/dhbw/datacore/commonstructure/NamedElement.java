package edu.horb.dhbw.datacore.commonstructure;

import edu.horb.dhbw.datacore.enums.VisibilityKind;
import edu.horb.dhbw.datacore.values.StringExpression;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class NamedElement extends Element {
    private String name;
    private String qualifiedName;
    private VisibilityKind visbility;
    private StringExpression nameExpression;
    private Namespace namespace;
}
