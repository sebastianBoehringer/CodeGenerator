package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.enums.ParameterDirectionKind;
import edu.horb.dhbw.datacore.uml.enums.ParameterEffectKind;
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
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
 * currently only inherits from {@link ConnectableElement}, the fields and
 * methods special to
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}
 * have been copied over.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Parameter extends ConnectableElement {
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
     * Defines if the values of the attribute should be ordered. This only
     * applies if the attribute can be multivalued, i. e. {@link #upper} is
     * greater than {@code 0}.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}.
     */
    private Boolean isOrdered = Boolean.FALSE;
    /**
     * Defines if the value of the attributes should be unique. This only
     * applies if the attribute can be multivalued, i. e. {@link #upper} is
     * greater than {@code 0}.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}.
     */
    private Boolean isUnique = Boolean.TRUE;
    /**
     * The lower bound of the cardinality. If this equals to {@code 0} the
     * attribute is optional. The value is derived by evaluating
     * {@link #lowerValue}.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}.
     */
    private Integer lower = 0;
    /**
     * The upper bound of the cardinality. If this is not less than {@code 2}
     * the attribute is multivalued. The value is derived by evaluation
     * {@link #upperValue}.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}.
     */
    private UnlimitedNatural upper = UnlimitedNatural.UNLIMITED;
    /**
     * A specification for the lower bound of the cardinality, i. e.
     * {@link #lower}.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}.
     */
    private ValueSpecification lowerValue;
    /**
     * A specification for the upper bound of the cardinality, i. e.
     * {@link #upper}.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}.
     */
    private ValueSpecification upperValue;
}
