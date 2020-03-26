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

package edu.horb.dhbw.inputprocessing.prevalidate.classification;

import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import org.thymeleaf.util.ListUtils;

public final class PropertyValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof Property;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        Property property = (Property) base;
        if (!property.getIsReadOnly() && property.getIsDerivedUnion()) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Property [%s] is derivedUnion but not readOnly",
                    property.getId()));
        }
        if (property.getIsComposite() && (property.getAssociation() != null)) {
            Property opposite = property.getOpposite();
            if (opposite.getUpper().getValue() > 1L) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "Property [%s] is composite but should belong to "
                                + "multiple others", property.getId()));
            }
        }
        if (property.getIsDerivedUnion() && !property.getIsDerived()) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Property [%s] is derived as union but derived is not "
                            + "true", property.getId()));
        }
        if (property.getAssociation() != null && !ListUtils
                .isEmpty(property.getQualifier())) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Property [%s] is qualified but does not belong"
                            + " to an association", property.getId()));
        }
        return VALID;
    }
}
