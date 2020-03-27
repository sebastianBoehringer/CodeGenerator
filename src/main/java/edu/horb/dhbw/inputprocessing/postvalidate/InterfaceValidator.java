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
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.datacore.model.Pair;

public class InterfaceValidator extends TypeValidator {
    public InterfaceValidator(final int upperLimit,
                              final FirstLetter nameStart) {

        super(upperLimit, nameStart);
    }

    @Override
    public boolean canValidate(final OOBase base) {

        if (base instanceof OOType) {
            OOType type = (OOType) base;
            return type.getType().equals(OOType.Type.INTERFACE);
        }
        return false;
    }

    @Override
    protected Pair<Boolean, String> continueTypeValidation(final OOType type) {

        return VALID;
    }
}
