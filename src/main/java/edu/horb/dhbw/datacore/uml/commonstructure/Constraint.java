package edu.horb.dhbw.datacore.uml.commonstructure;

import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
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
public class Constraint extends PackageableElement {
    private ValueSpecification specification;
    private Namespace context;
    private List<Element> constrainedElement = new ArrayList<>();

    public void setConstrainedElement(List<Element> constrainedElement) {

        this.constrainedElement.addAll(constrainedElement);
    }
}
