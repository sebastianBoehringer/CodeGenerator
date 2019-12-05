package edu.horb.dhbw.datacore.packages;

import edu.horb.dhbw.datacore.commonstructure.Element;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image extends Element {
    private String content;
    private String format;
    private String location;
}
