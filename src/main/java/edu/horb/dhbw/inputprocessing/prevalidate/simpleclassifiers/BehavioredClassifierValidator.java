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
import edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class BehavioredClassifierValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return false;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        BehavioredClassifier classifier = (BehavioredClassifier) base;
        if (classifier.getClassifierBehavior() != null
            && classifier.getClassifierBehavior().getSpecification() != null) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "BehavioredClassifier [%s] has a classifierBehavior with a "
                    + "specification", classifier.getId()));
        }
        return VALID;
    }
}
