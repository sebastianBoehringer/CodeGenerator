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

package edu.horb.dhbw.inputprocessing.prevalidate.simpleclassifiers;

import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.classification.Feature;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;

import java.util.ArrayList;
import java.util.List;

public final class InterfaceValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof Interface;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        Interface face = (Interface) base;
        //TODO leave this in while Classifier#feature is not used
        List<Feature> features = new ArrayList<>();
        features.addAll(face.getOwnedAttribute());
        features.addAll(face.getOwnedOperation());
        for (Feature feature : features) {
            if (!VisibilityKind.PUBLIC.equals(feature.getVisibility())) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "Interface [%s] has a non public feature",
                        face.getId()));
            }
        }
        return VALID;
    }
}
