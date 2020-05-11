/*
 * Copyright (c) 2020 Sebastian Boehringer.
 *  This file is part of the CodeGenerator.
 *
 *  CodeGenerator is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * CodeGenerator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License along with CodeGenerator.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.horb.dhbw.inputprocessing.restructure.statemachines;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonbehavior.Event;
import edu.horb.dhbw.datacore.uml.commonbehavior.Trigger;
import edu.horb.dhbw.inputprocessing.restructure.AbstractRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TriggerRestructurer extends AbstractRestructurer<Trigger> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public TriggerRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "trigger");
    }

    @Override
    public Trigger restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Trigger [{}]", id);
        Trigger trigger = new Trigger();
        trigger.setId(id);

        log.debug("Processing event for Trigger [{}]", id);
        trigger.setEvent(delegateRestructuring(element.getRefAttribute("event"),
                                               Event.class));
        log.debug("Processing umltype for Trigger [{}]", id);
        trigger.setUmlType(element.getPlainAttribute("umltype"));
        log.info("Completed restructuring of Trigger [{}]", id);
        return trigger;
    }
}
