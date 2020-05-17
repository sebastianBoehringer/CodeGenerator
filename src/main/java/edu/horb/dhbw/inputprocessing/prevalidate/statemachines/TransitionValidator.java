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
import edu.horb.dhbw.datacore.uml.enums.TransitionKind;
import edu.horb.dhbw.datacore.uml.statemachines.Transition;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class TransitionValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof Transition;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        Transition transition = (Transition) base;
        if (transition.getKind().equals(TransitionKind.EXTERNAL)) {
            if (transition.getTarget().getKind()
                        .equals(PseudostateKind.EXITPOINT) || transition
                        .getTarget().getKind()
                        .equals(PseudostateKind.ENTRYPOINT)) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "External Transition [%s] targets Entry- or ExitPoint",
                        transition.getId()));
            }
        }
        if (transition.getKind().equals(TransitionKind.INTERNAL)) {
            if (!transition.getTarget().getKind()
                    .equals(PseudostateKind.STATE)) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "Internal Transition [%s] targets a pseudoState",
                        transition.getId()));
            } else if (!transition.getTarget().getId()
                    .equals(transition.getSource().getId())) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "Internal Transition [%s] targets two "
                        + "different states", transition.getId()));
            }
        }
        //TODO constraint transition_vertices is not checked
        return VALID;
    }
}
