package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.enums.CallConcurrencyKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BehavioralFeature extends Feature {
    private CallConcurrencyKind concurrency = CallConcurrencyKind.SEQUENTIAL;
    private Boolean isAbstract = false;
    private List<Parameter> ownedParameter = new ArrayList<>();
    private List<ParameterSet> ownedParameterSet = new ArrayList<>();
    private Behavior method;
    private List<Type> raisedException = new ArrayList<>();

    public void setOwnedParameter(List<Parameter> ownedParameter) {

        this.ownedParameter.addAll(ownedParameter);
    }

    public void setOwnedParameterSet(List<ParameterSet> ownedParameterSet) {

        this.ownedParameterSet.addAll(ownedParameterSet);
    }
}
