package edu.horb.dhbw.datacore.uml.commonbehavior;

import edu.horb.dhbw.datacore.uml.classification.BehavioralFeature;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.classification.ParameterSet;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
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
public abstract class Behavior extends UMLClass {
    private Boolean isReentrant = Boolean.TRUE;
    private List<Parameter> ownedParameter = new ArrayList<>();
    private List<ParameterSet> ownedParameterSet = new ArrayList<>();
    private List<Constraint> postcondition = new ArrayList<>();
    private List<Constraint> precondition = new ArrayList<>();
    private BehavioralFeature specification;
    private BehavioredClassifier context;
}
