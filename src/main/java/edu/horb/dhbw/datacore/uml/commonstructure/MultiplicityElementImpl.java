package edu.horb.dhbw.datacore.uml.commonstructure;

import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.values.LiteralInteger;
import edu.horb.dhbw.datacore.uml.values.LiteralUnlimitedNatural;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Defines the cardinality of the attribute it is applied to.
 * See subclauses 7.5 and 7.8.8 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class MultiplicityElementImpl extends ElementImpl
        implements MultiplicityElement {
    /**
     * Defines if the values of the attribute should be ordered. This only
     * applies if the attribute can be multivalued, i. e. {@link #getUpper()}
     * returns a value greater than {@code 0}.
     */
    private Boolean isOrdered = Boolean.FALSE;
    /**
     * Defines if the value of the attributes should be unique. This only
     * applies if the attribute can be multivalued, i. e. {@link #getUpper()}
     * returns a value greater than {@code 0}.
     */
    private Boolean isUnique = Boolean.TRUE;

    /**
     * The lower bound of the cardinality. If this equals to {@code 0} the
     * attribute is optional. The value is derived by evaluating
     * {@link #lowerValue}.
     *
     * @return The lower limit of this element
     */
    public Integer getLower() {

        if (lowerValue == null) {
            return 1;
        }
        Integer lower;
        try {
            lower = ((LiteralInteger) lowerValue).getValue();
        } catch (ClassCastException e) {
            lower = 1;
        }
        return lower;
    }

    /**
     * The upper bound of the cardinality. If this is not less than {@code 2}
     * the attribute is multivalued. The value is derived by evaluation
     * {@link #upperValue}.
     *
     * @return The upper limit of this element
     */
    public UnlimitedNatural getUpper() {
        if (upperValue == null) {
            return UnlimitedNatural.ONE;
        }
        UnlimitedNatural upper;
        try {
            upper = ((LiteralUnlimitedNatural) upperValue).getValue();
        } catch (ClassCastException e) {
            upper = UnlimitedNatural.ONE;
        }
        return upper;
    }


    /**
     * A specification for the lower bound of the cardinality.
     */
    private ValueSpecification lowerValue;
    /**
     * A specification for the upper bound of the cardinality.
     */
    private ValueSpecification upperValue;
}
