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

package edu.horb.dhbw.inputprocessing.prevalidate;

import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.uml.XMIElement;

public interface IPreValidator {
    /**
     * A check to see whether the validator can handle the given element.
     * This allows for more complex logic than simple type checking. E. g. a
     * validator that validates only
     * {@link edu.horb.dhbw.datacore.uml.packages.UMLPackage} whose name
     * starts with {@code 'P'}.
     *
     * @param base The element to evaluate
     * @return {@code True} if the validator can validate the element.
     */
    boolean canValidate(XMIElement base);

    /**
     * Applies the validation logic of the validator on the passed parameter.
     * The result is a {@link Pair} where {@link Pair#first} is {@code true} if
     * the validator deems the object valid and {@code false} otherwise.
     * {@link Pair#second} transports the error message.
     *
     * @param base The object to validate
     * @return A {@link Pair} holding whether the validation was successful
     * and a possible error message.
     */
    Pair<Boolean, String> validate(XMIElement base);
}
