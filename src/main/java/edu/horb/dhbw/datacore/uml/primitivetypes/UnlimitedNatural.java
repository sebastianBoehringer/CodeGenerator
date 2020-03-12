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

package edu.horb.dhbw.datacore.uml.primitivetypes;

import edu.horb.dhbw.datacore.uml.simpleclassifiers.PrimitiveTypeImpl;
import lombok.Data;
import org.thymeleaf.util.StringUtils;

/**
 * A class representing the primitive datatype UnlimitedNatural.
 * It can hold the numbers 0, 1, 2, ... and onward. Furthermore there is the
 * special value called unlimited which is represented by an asterisk "*".
 * Since the language does not allow for such a value it is set to
 * {@link Long#MAX_VALUE} instead. Another option would be to use
 * {@link Long#MIN_VALUE} but that would make comparisons feel awkward.
 */
@Data
public final class UnlimitedNatural extends PrimitiveTypeImpl {
    /**
     * Represents the special value unlimited as {@link Long#MAX_VALUE}.
     * Since UnlimitedNatural is most often used in conjunction with
     * {@link edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement}
     * it is assumed that this value will never be used as an actual value
     * for an upper (or even lower) bound of the multiplicity element. This
     * is reaffirmed by the fact that the most used multiplicities are [0,1]
     * for optional things, [1,1] to require things, [1,*] to symbolize a
     * relationship of one to many and [*,*] to represent a many to many
     * relationship.
     */
    public static final UnlimitedNatural UNLIMITED = new UnlimitedNatural("*");
    /**
     * The value of this unlimitedNatural.
     */
    private long value;

    /**
     * Constructs a new UnlimitedNatural from the String.
     * If the string is {@code null} or empty, {@link #value} will be set to
     * {@code 0}.
     * The constructor throws {@link IllegalArgumentException} if the parameter
     * is not a valid representation of a long or an asterisk (*). It also
     * throws the exception if the parsed value is negative, i. e. less than
     * {@code 0}.
     *
     * @param string The string from which the value should be parsed.
     */
    public UnlimitedNatural(final String string) {

        try {
            long intermediate = Long.parseLong(string);
            if (intermediate < 0) {
                throw new IllegalArgumentException(
                        String.format("Parsed value [%d] is less than 0",
                                      intermediate));
            }
        } catch (NumberFormatException e) {
            if ("*".equals(string)) {
                value = Long.MAX_VALUE;
            } else {
                if (StringUtils.isEmpty(string)) {
                    //default value for literalunlimited natural is 0
                    value = 0L;
                } else {
                    throw new IllegalArgumentException(String.format(
                            "Could not parse [%s] as long and it wasnt "
                                    + "[*] either", string));
                }
            }
        }
    }

    /**
     * Constructs a new UnlimitedNatural by boxing the given parameter.
     * This throws an {@link IllegalArgumentException} if the paramter is
     * null or less than {@code 0}.
     *
     * @param nestedValue The long value this unlimitedNatural should wrap
     */
    public UnlimitedNatural(final Long nestedValue) {

        if (nestedValue == null || nestedValue < 0) {
            throw new IllegalArgumentException(
                    String.format("Parsed value [%d] is less than 0 or null",
                                  nestedValue));
        }
    }

    @Override
    public String getName() {

        return "unlimitednatural";
    }
}
