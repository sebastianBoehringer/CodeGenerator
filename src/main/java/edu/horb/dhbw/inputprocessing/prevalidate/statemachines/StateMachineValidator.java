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
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import lombok.EqualsAndHashCode;
import org.thymeleaf.util.ListUtils;

@EqualsAndHashCode
public final class StateMachineValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof StateMachine;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        StateMachine machine = (StateMachine) base;
        for (State state : machine.getConnectionPoint()) {
            if (!PseudostateKind.EXITPOINT.equals(state.getKind())
                || !PseudostateKind.ENTRYPOINT.equals(state.getKind())) {
                return new Pair<>(Boolean.FALSE, String.format(
                        "StateMachine [%s] has connectionPoints "
                        + "that are not of kind exit- or " + "entrypoint",
                        machine.getId()));
            }
        }
        //TODO constraint classifier_context is not checked
        //TODO constraint context_classifier is not checked
        if (machine.getSpecification() != null && !ListUtils
                .isEmpty(machine.getConnectionPoint())) {
            return new Pair<>(Boolean.FALSE, String.format(
                    "StateMachine [%s] implements a "
                    + "BehavioralFeature but has connectionPoints",
                    machine.getId()));
        }
        return VALID;
    }
}
