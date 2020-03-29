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

package edu.horb.dhbw.inputprocessing.prevalidate.packages;

import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class PackageValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof UMLPackage;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        UMLPackage pkg = (UMLPackage) base;
        for (PackageableElement element : pkg.getPackagedElement()) {
            if (!VisibilityKind.PUBLIC.equals(element.getVisibility())
                    && !VisibilityKind.PRIVATE
                    .equals(element.getVisibility())) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "Package [%s] packaged an element with a "
                                + "visibility different from public and "
                                + "private", pkg.getId()));
            }
        }
        return VALID;
    }
}
