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
import edu.horb.dhbw.datacore.model.OOMethod;
import edu.horb.dhbw.datacore.model.Pair;

public class MethodValidator extends NamingValidator {
    public MethodValidator(final FirstLetter nameStart) {

        super(nameStart);
    }

    @Override
    public final boolean canValidate(final OOBase base) {

        return base instanceof OOMethod;
    }

    @Override
    public Pair<Boolean, String> continueValidation(final OOBase base) {

        OOMethod method = (OOMethod) base;
        if (method.isAbstract() && method.getLogic() != null) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Method [%s] is abstract but has a body defined",
                    method.getName()));
        }
        return VALID;
    }
}
