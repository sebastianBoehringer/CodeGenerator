package edu.horb.dhbw.datacore.commonbehavior;

import edu.horb.dhbw.datacore.classification.BehavioralFeature;
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
public class OpaqueBehavior extends BehavioralFeature {
    private List<String> body = new ArrayList<>();
    private List<String> language = new ArrayList<>();

    public void setBody(List<String> body) {

        this.body.addAll(body);
    }

    public void setLanguage(List<String> language) {

        this.language.addAll(language);
    }
}
