package edu.horb.dhbw.datacore.packages;

import edu.horb.dhbw.datacore.structuredclassifiers.UMLClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stereotype extends UMLClass {
    private List<Image> icon = new ArrayList<>();
    private Profile profile;

    public void setIcon(List<Image> icon) {

        this.icon.addAll(icon);
    }
}
