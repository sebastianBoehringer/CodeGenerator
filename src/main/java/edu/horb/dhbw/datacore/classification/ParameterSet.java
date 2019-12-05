package edu.horb.dhbw.datacore.classification;

import edu.horb.dhbw.datacore.commonstructure.Constraint;
import edu.horb.dhbw.datacore.commonstructure.NamedElement;
import lombok.Data;

import java.util.List;

@Data
public class ParameterSet extends NamedElement {
    private List<Constraint> condition;
    private List<Parameter> parameter;

    public void setCondition(List<Constraint> condition) {

        this.condition.addAll(condition);
    }

    public void setParameter(List<Parameter> parameter) {

        this.parameter.addAll(parameter);
    }
}
