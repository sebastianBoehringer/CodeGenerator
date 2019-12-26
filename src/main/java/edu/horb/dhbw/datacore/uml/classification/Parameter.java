package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.enums.ParameterDirectionKind;
import edu.horb.dhbw.datacore.uml.enums.ParameterEffectKind;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ConnectableElement;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an argument for the invocation of a behavior.
 * See subclauses 9.4 (especially 9.4.3.4) and 9.9.13 of the UML specification
 * for more details.
 * This should specializes both {@link ConnectableElement} and
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}. It
 * currently only inherits from {@link ConnectableElement}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter extends ConnectableElement {
    //TODO resolve inheritance
    /**
     * Represents a default value that should be used if no value is passed
     * for this parameter.
     */
    private String defaults;
    /**
     * Indicates if this parameter goes into or comes out of a method.
     */
    private ParameterDirectionKind direction = ParameterDirectionKind.IN;
    /**
     * Indicates the effect the execution of the behavior has on objects
     * passed into it for this parameter.
     */
    private ParameterEffectKind effect;
    /**
     * If this is {@code true} this outbound parameter can emit an exception
     * and thus supress the output of other parameters.
     */
    private Boolean isException = Boolean.FALSE;
    /**
     * If this is {@code true} the parameter accepts the input of values
     * while the execution of the behavior is ongoing.
     */
    private Boolean isStream = Boolean.FALSE;
    /**
     * A valueSpecification for the default value.
     */
    private ValueSpecification defaultValue;

    /**
     * The operation that is using this parameter.
     */
    private Operation operation;

    /**
     * The parameterSets this parameter is a part of.
     */
    private List<ParameterSet> parameterSet = new ArrayList<>();

    /**
     * Adds a new parameterSet to {@link #parameterSet}.
     *
     * @param set The parameterSet to add
     */
    public void addParameterSet(final ParameterSet set) {

        parameterSet.add(set);
    }
}
