package edu.horb.dhbw.datacore.uml.simpleclassifiers;

import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
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
public abstract class BehavioredClassifier extends Classifier {

    private List<InterfaceRealization> interfaceRealization = new ArrayList<>();
    private List<Behavior> ownedBehavior = new ArrayList<>();
    private Behavior classifierBehavior;

    /**
     * Instead of doing what the name implies this adds {@link InterfaceRealization}s to
     * {@link #interfaceRealization} instead of setting it. This has to do with using Jackson for
     * XML
     * Marshalling which is calling the setter each time it finds a fitting element. This
     * behavior would overwrite all found values before the last one. See for example
     * <a href="https://github.com/FasterXML/jackson-dataformat-xml/issues/363"> this github issue
     * </a>
     *
     * @param interfaceRealization A newly realized interface
     */
    public void setInterfaceRealization(List<InterfaceRealization> interfaceRealization) {

        this.interfaceRealization.addAll(interfaceRealization);
    }

    public void setOwnedBehavior(List<Behavior> ownedBehavior) {

        this.ownedBehavior.addAll(ownedBehavior);
    }
}
