package edu.horb.dhbw.datacore.commonbehavior;

import edu.horb.dhbw.datacore.commonstructure.PackageableElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public abstract class Event extends PackageableElement {
}
