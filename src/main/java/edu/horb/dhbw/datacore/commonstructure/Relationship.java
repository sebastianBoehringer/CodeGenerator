package edu.horb.dhbw.datacore.commonstructure;

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
public abstract class Relationship extends Element {
    private List<Element> relatedElement = new ArrayList<>();

    public void setRelatedElement(List<Element> relatedElement) {

        this.relatedElement.addAll(relatedElement);
    }
}
