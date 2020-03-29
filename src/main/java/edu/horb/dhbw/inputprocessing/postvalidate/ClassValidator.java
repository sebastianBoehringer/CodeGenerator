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
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.datacore.model.Pair;
import lombok.EqualsAndHashCode;

import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
public class ClassValidator extends TypeValidator {

    public ClassValidator(final int upperLimit, final FirstLetter nameStart) {

        super(upperLimit, nameStart);
    }

    @Override
    public boolean canValidate(final OOBase base) {

        if (base instanceof OOType) {
            OOType type = (OOType) base;
            return OOType.Type.CLASS.equals(type.getType());
        }
        return false;
    }

    @Override
    protected Pair<Boolean, String> continueTypeValidation(final OOType type) {

        // only abstract classes may have abstract methods
        if (!type.isAbstract()) {
            Optional<OOMethod> abstracts =
                    type.getMethods().stream().filter(OOMethod::isAbstract)
                            .findFirst();
            if (abstracts.isPresent()) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "Class [%s] is not abstract but has abstract method",
                        type.getName()));
            }
        }
        return VALID;
    }
}
