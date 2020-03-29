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

package edu.horb.dhbw.inputprocessing.prevalidate.statemachines;

import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.enums.PseudostateKind;
import edu.horb.dhbw.datacore.uml.statemachines.Region;
import edu.horb.dhbw.datacore.uml.statemachines.State;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public final class RegionValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof Region;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        Region region = (Region) base;
        if (region.getState() != null && region.getStateMachine() != null) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Region [%s] is owned by a state and a " + "stateMachine",
                    region.getId()));
        }
        List<State> initialStates = region.getSubvertex().stream()
                .filter(s -> PseudostateKind.INITIAL.equals(s.getKind()))
                .collect(Collectors.toList());
        if (initialStates.size() > 1) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "Region [%s] contains more than 1 initial state",
                    region.getId()));
        }
        return VALID;
    }
}
