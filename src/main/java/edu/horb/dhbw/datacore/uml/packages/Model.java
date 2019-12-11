package edu.horb.dhbw.datacore.uml.packages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model extends UMLPackage {
    private String viewpoint;
    private String version;
}
