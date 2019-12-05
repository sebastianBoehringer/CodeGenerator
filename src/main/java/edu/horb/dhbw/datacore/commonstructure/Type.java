package edu.horb.dhbw.datacore.commonstructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public abstract class Type extends NamedElement {

    public Type(String typename) {

        this.setName(typename);
    }
}
