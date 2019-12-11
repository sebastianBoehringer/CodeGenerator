package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
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
