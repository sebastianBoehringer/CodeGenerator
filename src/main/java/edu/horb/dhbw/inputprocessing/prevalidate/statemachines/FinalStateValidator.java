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
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class FinalStateValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        if (base instanceof State) {
            State state = (State) base;
            return PseudostateKind.FINAL.equals(state.getKind());
        }
        return false;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        State state = (State) base;
        if (state.getRegion().size() != 0) {
            return new Pair<>(Boolean.FALSE,
                              String.format("FinalState [%s] has regions",
                                            state.getId()));
        }
        if (state.getExit() != null) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "FinalState [%s] has an exit behavior", state.getId()));
        }
        if (state.getEntry() != null) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "FinalState [%s] has an entry behavior", state.getId()));
        }
        if (state.getDoActivity() != null) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "FinalState [%s] has an doActivity behavior",
                    state.getId()));
        }
        if (state.getOutgoing().size() != 0) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "FinalState [%s] has outgoing transitions", state.getId()));
        }
        if (state.getSubmachine() != null) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "FinalState [%s] is referencing a submachine",
                    state.getId()));
        }
        return VALID;
    }
}
