package edu.horb.dhbw.datacore.classification;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import edu.horb.dhbw.datacore.commonstructure.Constraint;
import edu.horb.dhbw.datacore.commonstructure.Type;
import edu.horb.dhbw.datacore.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.simpleclassifiers.DataType;
import edu.horb.dhbw.datacore.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.structuredclassifiers.UMLClass;
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
public class Operation extends BehavioralFeature {
    private Boolean isOrdered;
    private Boolean isQuery = Boolean.FALSE;
    private Boolean isUnique;
    private Integer lower;
    private UnlimitedNatural upper;
    private Constraint bodyCondition;
    private List<Parameter> ownedParameter = new ArrayList<>();
    private List<Constraint> postcondition = new ArrayList<>();
    private List<Constraint> precondition = new ArrayList<>();

    //private Type type;

    private List<Type> raisedException = new ArrayList<>();
    @JacksonXmlProperty(localName = "interface")
    private Interface anInterface;
    @JacksonXmlProperty(localName = "class")
    private UMLClass umlClass;
    private DataType datatype;

    public void setOwnedParameter(List<Parameter> ownedParameter) {

        this.ownedParameter.addAll(ownedParameter);
    }

    public void setPostcondition(List<Constraint> postcondition) {

        this.postcondition.addAll(postcondition);
    }

    public void setPrecondition(List<Constraint> precondition) {

        this.precondition.addAll(precondition);
    }
}
