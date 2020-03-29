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
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public abstract class TypeValidator extends NamingValidator {
    /**
     * The number of classes the class is allowed to extend.
     */
    private final int upperLimit;

    /**
     * @param upperLimit Defines how many supertypes the type can have
     * @param nameStart  Defines which case the first letter of the name must
     *                   have
     */
    public TypeValidator(final int upperLimit, final FirstLetter nameStart) {

        super(nameStart);
        this.upperLimit = upperLimit;
    }

    /**
     * {@inheritDoc}
     * Subclasses may still want to override this method to ensure they are
     * validating the correct
     * {@link edu.horb.dhbw.datacore.model.OOType.Type} of {@link OOType}.
     *
     * @param base The element to evaluate
     * @return {@code true} if the parameter is an instance of {@link OOType}
     * , {@code false} otherwise
     */
    @Override
    public boolean canValidate(final OOBase base) {

        return base instanceof OOType;
    }

    @Override
    public final Pair<Boolean, String> continueValidation(final OOBase base) {

        OOType type = (OOType) base;
        if (type.getSuperTypes().size() > upperLimit) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Type [%s] extends more than the allowed [%d] types",
                    type.getName(), upperLimit));
        }

        if (!VisibilityKind.PUBLIC.equals(type.getVisibility())
                && !VisibilityKind.PACKAGE.equals(type.getVisibility())) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Type [%s] is not visible to any other type",
                    type.getName()));
        }
        for (OOType superType : type.getSuperTypes()) {
            if (superType.isFinal()) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "Type [%s] extends final type [%s]", type.getName(),
                        superType.getName()));
            }
        }
        return continueTypeValidation(type);
    }

    /**
     * Validates the points specific to each subclass.
     *
     * @param type The type to validate
     * @return {@link IPostValidator#VALID} if the validator deems the type
     * correct, a pair with {@link Boolean#FALSE} and an error message otherwise
     */
    protected abstract Pair<Boolean, String> continueTypeValidation(OOType type);
}
