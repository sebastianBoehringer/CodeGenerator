package edu.horb.dhbw.datacore.values;

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
public class StringExpression extends Expression {
    private List<StringExpression> subExpression = new ArrayList<>();
    private StringExpression owningExpression;

    public void setSubExpression(List<StringExpression> subExpression) {

        this.subExpression.addAll(subExpression);
    }
}
