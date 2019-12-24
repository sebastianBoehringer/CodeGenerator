package edu.horb.dhbw.datacore.uml.commonstructure;

import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Defines the cardinality of the attribute it is applied to.
 * <br/>
 * See subclauses 7.5 and 7.8.8 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class MultiplicityElement extends Element {
    /**
     * Defines if the values of the attribute should be ordered. This only
     * applies if the attribute can be multivalued, i. e. {@link #upper} is
     * greater than {@code 0}.
     */
    private Boolean ordered = false;
    /**
     * Defines if the value of the attributes should be unique. This only
     * applies if the attribute can be multivalued, i. e. {@link #upper} is
     * greater than {@code 0}.
     */
    private Boolean unique = true;
    /**
     * The lower bound of the cardinality. If this equals to {@code 0} the
     * attribute is optional. The value is derived by evaluating
     * {@link #lowerValue}.
     */
    private Integer lower = 0;
    /**
     * The upper bound of the cardinality. If this is not less than {@code 2}
     * the attribute is multivalued. The value is derived by evaluation
     * {@link #upperValue}.
     */
    private UnlimitedNatural upper = UnlimitedNatural.UNLIMITED;
    /**
     * A specification for the lower bound of the cardinality, i. e.
     * {@link #lower}.
     */
    private ValueSpecification lowerValue;
    /**
     * A specification for the upper bound of the cardinality, i. e.
     * {@link #upper}.
     */
    private ValueSpecification upperValue;
}
