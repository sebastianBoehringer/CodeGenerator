package edu.horb.dhbw.datacore.commonbehavior;

import edu.horb.dhbw.datacore.classification.BehavioralFeature;
import edu.horb.dhbw.datacore.classification.Parameter;
import edu.horb.dhbw.datacore.classification.ParameterSet;
import edu.horb.dhbw.datacore.commonstructure.Constraint;
import edu.horb.dhbw.datacore.simpleclassifiers.BehavioredClassifier;
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
public abstract class Behavior extends UMLClass {
    private Boolean isReentrant = Boolean.TRUE;
    private List<Parameter> ownedParameter = new ArrayList<>();
    private List<ParameterSet> ownedParameterSet = new ArrayList<>();
    private List<Constraint> postcondition = new ArrayList<>();
    private List<Constraint> precondition = new ArrayList<>();
    private BehavioralFeature specification;
    private BehavioredClassifier context;

    public void setOwnedParameter(List<Parameter> ownedParameter) {

        this.ownedParameter.addAll(ownedParameter);
    }

    public void setOwnedParameterSet(List<ParameterSet> ownedParameterSet) {

        this.ownedParameterSet.addAll(ownedParameterSet);
    }

    public void setPostcondition(List<Constraint> postcondition) {

        this.postcondition.addAll(postcondition);
    }

    public void setPrecondition(List<Constraint> precondition) {

        this.precondition.addAll(precondition);
    }
}
