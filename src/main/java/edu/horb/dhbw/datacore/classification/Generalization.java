package edu.horb.dhbw.datacore.classification;

import edu.horb.dhbw.datacore.commonstructure.DirectedRelationship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Generalization extends DirectedRelationship {
    private Boolean isSubstitutable = Boolean.TRUE;
    private Classifier specific;
    private Classifier general;

    private List<GeneralizationSet> generalizationSet = new ArrayList<>();

    public void setGeneralizationSet(List<GeneralizationSet> generalizationSet) {

        this.generalizationSet.addAll(generalizationSet);
    }
}
