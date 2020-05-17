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
import edu.horb.dhbw.datacore.uml.statemachines.Transition;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class PseudoStateValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        if (base instanceof State) {
            State state = (State) base;
            return !PseudostateKind.FINAL.equals(state.getKind())
                   && !PseudostateKind.STATE.equals(state.getKind());
        }
        return false;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        State state = (State) base;
        switch (state.getKind()) {

            case INITIAL:
                if (state.getOutgoing().size() > 1) {
                    return new Pair<>(Boolean.FALSE, String.format(
                            "Initial PseudoState [%s] has more"
                            + " than 1 outgoing transition", state.getId()));
                }
                if (state.getOutgoing().size() == 1) {
                    Transition t = state.getOutgoing().get(0);
                    if (t.getGuard() != null) {
                        return new Pair<>(Boolean.FALSE, String.format(
                                "Transition [%s] outgoing from Initial "
                                + "PseudoState [%s] has a guard", t.getId(),
                                state.getId()));
                    }
                }
                break;
            case DEEPHISTORY:
            case SHALLOWHISTORY:
                return new Pair<>(Boolean.FALSE, "The codegenerator does not "
                                                 + "support history states");
            case JOIN:
                return new Pair<>(Boolean.FALSE, String.format(
                        "The codegenerator does not support composite "
                        + "states. Thus the constraint "
                        + "\"transitions_incoming\" for PseudoState "
                        + "[%s] can never be fulfilled", state.getId()));
            case FORK:
                return new Pair<>(Boolean.FALSE, String.format(
                        "The codegenerator does not support composite "
                        + "states. Thus the constraint "
                        + "\"transitions_outgoing\" for PseudoState "
                        + "[%s] can never be fulfilled", state.getId()));
            case JUNCTION:
            case CHOICE:
                if (state.getOutgoing().size() < 1) {
                    return new Pair<>(Boolean.FALSE, String.format(
                            "PseudoState [%s] must have at least one outgoing "
                            + "transition", state.getId()));
                }
                if (state.getIncoming().size() < 1) {
                    return new Pair<>(Boolean.FALSE, String.format(
                            "PseudoState [%s] must have at least one incoming "
                            + "transition", state.getId()));
                }

                break;
            case ENTRYPOINT:
            case EXITPOINT:
            case TERMINATE:
                break;
            case FINAL:
            case STATE:
            default:
                throw new IllegalArgumentException(
                        "Cannot validate a State of kind " + state.getKind());
        }
        return VALID;
    }
}
