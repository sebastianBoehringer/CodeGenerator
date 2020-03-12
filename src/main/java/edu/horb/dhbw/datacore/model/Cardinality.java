/*
 * Copyright (c) 2020 Sebastian Boehringer.
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

package edu.horb.dhbw.datacore.model;

import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import lombok.NonNull;

public enum Cardinality {
    /**
     * Implies a lower bound of 0 and a upper bound of 1.
     */
    OPTIONAL,
    /**
     * Implies that both the upper bound and lower bound are 1.
     */
    SINGLE,
    /**
     * Implies that there are multiple, ordered elements. Uniqueness is not
     * enforced.
     */
    LIST,
    /**
     * Implies that there are multiple, unordered elements. Uniqueness is not
     * enforced.
     */
    BAG,
    /**
     * Implies that there are multiple, unordered elements. Uniqueness is
     * enforced.
     */
    SET,
    /**
     * Implies that there are mutliple, ordered elements. Uniqueness is
     * enforced.
     */
    ORDERED_SET;

    public static Cardinality getCorrectCardinality(final @NonNull Boolean unique, final @NonNull Boolean ordered, final @NonNull Integer lower, final @NonNull UnlimitedNatural upper) {

        if (upper.getValue() <= 1L) {
            if (lower == 0) {
                return OPTIONAL;
            } else {
                return SINGLE;
            }
        } else {
            if (unique) {
                if (ordered) {
                    return ORDERED_SET;
                } else {
                    return SET;
                }
            } else {
                if (ordered) {
                    return LIST;
                } else {
                    return BAG;
                }
            }
        }
    }
}
