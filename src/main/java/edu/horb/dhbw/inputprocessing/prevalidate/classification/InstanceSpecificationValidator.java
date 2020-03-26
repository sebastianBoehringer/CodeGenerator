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
import edu.horb.dhbw.datacore.uml.classification.InstanceSpecification;
import edu.horb.dhbw.datacore.uml.classification.Slot;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class InstanceSpecificationValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof InstanceSpecification;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        InstanceSpecification specification = (InstanceSpecification) base;
        List<Slot> slots = specification.getSlot();
        Set<String> featureIds =
                slots.stream().map(s -> s.getDefiningFeature().getId())
                        .collect(Collectors.toSet());
        if (slots.size() != featureIds.size()) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "InstanceSpecification [%s] had slots that "
                            + "defined the same feature more than once",
                    specification.getId()));
        }
        //TODO
        return VALID;
    }
}
