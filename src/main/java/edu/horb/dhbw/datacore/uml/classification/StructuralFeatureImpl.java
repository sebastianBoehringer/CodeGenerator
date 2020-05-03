/*
 * Copyright (c) 2019 Sebastian Boehringer.
 *  This file is part of the CodeGenerator.
 *
 *  CodeGenerator is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 *  option) any later version.
 * CodeGenerator is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public
 * License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 * along with CodeGenerator.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.values.LiteralInteger;
import edu.horb.dhbw.datacore.uml.values.LiteralUnlimitedNatural;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Describes part of the structure of a classifier.
 * See subclauses 9.4 and 9.9.21 of the UML specification for more details.
 * This should specialize
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement},
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.TypedElement} and
 * {@link Feature}. It only inherits from {@link Feature}.
 * The fields of the other two classes have been copied over which is
 * definitely not the prettiest solution out there.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class StructuralFeatureImpl extends FeatureImpl
        implements StructuralFeature {
    /**
     * If this is {@code true} the feature cannot be written to after
     * initialization.
     */
    private Boolean isReadOnly = Boolean.FALSE;

    /**
     * Defines if the values of the attribute should be ordered. This only
     * applies if the attribute can be multivalued, i. e. {@link #getUpper()}
     * returns a value greater than {@code 0}.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}.
     */
    private Boolean isOrdered = Boolean.FALSE;
    /**
     * Defines if the value of the attributes should be unique. This only
     * applies if the attribute can be multivalued, i. e. {@link #getUpper()}
     * returns a value greater than {@code 0}.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}.
     */
    private Boolean isUnique = Boolean.TRUE;
    /**
     * A specification for the lower bound of the cardinality.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}.
     */
    private ValueSpecification lowerValue;
    /**
     * A specification for the upper bound of the cardinality.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}.
     */
    private ValueSpecification upperValue;

    /**
     * The lower bound of the cardinality. If this equals to {@code 0} the
     * attribute is optional. The value is derived by evaluating
     * {@link #lowerValue}.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}.
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
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}.
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
     * The type of this element.
     * Copied from
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.TypedElement}.
     */
    private Type type;
}
