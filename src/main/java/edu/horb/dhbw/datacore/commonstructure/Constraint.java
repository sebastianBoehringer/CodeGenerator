package edu.horb.dhbw.datacore.commonstructure;

import edu.horb.dhbw.datacore.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Constraint {
    private ValueSpecification specification;
    private Namespace context;
    private List<Element> constrainedElement = new ArrayList<>();

    public void setConstrainedElement(List<Element> constrainedElement) {

        this.constrainedElement.addAll(constrainedElement);
    }
}
