package edu.horb.dhbw.datacore.values;

import edu.horb.dhbw.datacore.classification.Parameter;
import edu.horb.dhbw.datacore.commonbehavior.Behavior;
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
public class OpaqueExpression extends ValueSpecification {
    private List<String> body = new ArrayList<>();
    private List<String> language = new ArrayList<>();
    private Behavior behavior;
    private Parameter result;

    public void setBody(List<String> body) {

        this.body.addAll(body);
    }

    public void setLanguage(List<String> language) {

        this.language.addAll(language);
    }
}
