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

package edu.horb.dhbw.inputprocessing.prevalidate.commonbehavior;

import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.commonbehavior.MessageEvent;
import edu.horb.dhbw.datacore.uml.commonbehavior.Trigger;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import org.thymeleaf.util.ListUtils;

public final class TriggerValidator implements IPreValidator {
    @Override
    public boolean canValidate(final XMIElement base) {

        return base instanceof Trigger;
    }

    @Override
    public Pair<Boolean, String> validate(final XMIElement base) {

        Trigger trigger = (Trigger) base;
        if (!ListUtils.isEmpty(trigger.getPorts()) && !(trigger
                .getEvent() instanceof MessageEvent)) {
            return new Pair<>(false, String.format(
                    "Trigger %s has ports but its event is not a message"
                            + " event but %s", trigger.getId(),
                    trigger.getEvent().getClass().getSimpleName()));
        } return VALID;
    }
}
