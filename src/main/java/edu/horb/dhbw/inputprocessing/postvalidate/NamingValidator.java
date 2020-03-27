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

package edu.horb.dhbw.inputprocessing.postvalidate;

import edu.horb.dhbw.datacore.model.OOBase;
import edu.horb.dhbw.datacore.model.Pair;

public abstract class NamingValidator implements IPostValidator {
    /**
     * Determines how the name of the element to validate has to start.
     */
    private final FirstLetter nameStart;

    public NamingValidator(final FirstLetter nameStart) {

        this.nameStart = nameStart;
    }

    @Override
    public final Pair<Boolean, String> validate(final OOBase base) {

        if (FirstLetter.UPPER.equals(nameStart) && !Character
                .isUpperCase(base.getName().charAt(0))) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Element [%s] does not start with a uppercase letter but "
                            + "has to", base.getName()));
        }
        if (FirstLetter.LOWER.equals(nameStart) && !Character
                .isLowerCase(base.getName().charAt(0))) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Element [%s] does not start with a lowercase letter but "
                            + "has to", base.getName()));
        }
        return continueValidation(base);
    }

    protected abstract Pair<Boolean, String> continueValidation(OOBase base);
}
