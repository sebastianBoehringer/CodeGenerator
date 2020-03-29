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

package edu.horb.dhbw.inputprocessing.prevalidate.commonstructure;

import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImport;
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import lombok.EqualsAndHashCode;
import org.thymeleaf.util.ListUtils;

@EqualsAndHashCode
public final class NamespaceValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof Namespace;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        Namespace namespace = (Namespace) base;
        if (!ListUtils.isEmpty(namespace.getElementImport())) {
            for (ElementImport elementImport : namespace.getElementImport()) {
                if (namespace.getOwnedMember()
                        .contains(elementImport.getImportedElement())) {
                    return new Pair<>(Boolean.FALSE, String.format(
                            "Namespace [%s] imports an element it " + "owns",
                            namespace.getId()));
                }
            }
        }

        return VALID;
    }
}
