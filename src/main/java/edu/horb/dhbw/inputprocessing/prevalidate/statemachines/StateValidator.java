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
import edu.horb.dhbw.datacore.uml.statemachines.State;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import org.thymeleaf.util.ListUtils;

public final class StateValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        if (base instanceof State) {
            return ((State) base).getKind().equals(PseudostateKind.STATE);
        }
        return false;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        State state = (State) base;
        for (State point : state.getConnectionPoint()) {
            if (!PseudostateKind.ENTRYPOINT.equals(point.getKind())
                    || !PseudostateKind.EXITPOINT.equals(point.getKind())) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "State [%s] contains connectionpoints that are not "
                                + "entry- or exitpoints", state.getId()));
            }
        }
        if (!state.isSubmachineState() && !ListUtils
                .isEmpty(state.getConnectionPoint())) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "State [%s] owns connectionPoints but has no "
                            + "submachine", state.getId()));
        }
        if (state.isSubmachineState() && state.isComposite()) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "State [%s] is both a composite and a "
                            + "submachine state", state.getId()));
        }
        return VALID;
    }
}
