package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.commonstructure.DirectedRelationship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Generalization extends DirectedRelationship {
    private Boolean isSubstitutable;
    private Classifier specific;
    private Classifier general;
    private List<GeneralizationSet> generalizationSet = new ArrayList<>();


}
