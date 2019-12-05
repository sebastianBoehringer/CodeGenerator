package edu.horb.dhbw.datacore.commonstructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class DirectedRelationship extends Relationship {
    List<Element> source = new ArrayList<>();
    List<Element> target = new ArrayList<>();

    public void setSource(List<Element> source) {

        this.source.addAll(source);
    }

    public void setTarget(List<Element> target) {

        this.target.addAll(target);
    }
}
