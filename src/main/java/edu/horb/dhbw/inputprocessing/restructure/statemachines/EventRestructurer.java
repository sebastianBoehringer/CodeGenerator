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

package edu.horb.dhbw.inputprocessing.restructure.statemachines;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.commonbehavior.AnyReceiveEvent;
import edu.horb.dhbw.datacore.uml.commonbehavior.CallEvent;
import edu.horb.dhbw.datacore.uml.commonbehavior.ChangeEvent;
import edu.horb.dhbw.datacore.uml.commonbehavior.Event;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.inputprocessing.restructure.AbstractRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public final class EventRestructurer extends AbstractRestructurer<Event> {
    /**
     * The types of events this restructurer handles.
     */
    private static final List<String> EVENT_TYPES =
            Arrays.asList("anyreceiveevent", "callevent", "changeevent");

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public EventRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "event");
    }

    @Override
    public boolean canRestructure(@NonNull final ModelElement element) {

        return EVENT_TYPES.contains(XMIUtil.getUMLType(element));
    }

    @Override
    public Event restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Event [{}]", id);
        Event event;
        switch (element.getPlainAttribute("kind")) {
            case "call":
                event = new CallEvent();
                ((CallEvent) event).setOperation(delegateRestructuring(
                        element.getRefAttribute("linkedeventelement"),
                        Operation.class));
                break;
            case "change":
                event = new ChangeEvent();
                ((ChangeEvent) event).setChangeExpression(delegateRestructuring(
                        element.getRefAttribute("linkedeventelement"),
                        ValueSpecification.class));
                break;
            case "anyreceive":
                event = new AnyReceiveEvent();
                break;
            default:
                log.error("Event kind [{}] is unknown",
                          element.getPlainAttribute("kind"));
                event = new ChangeEvent();
        }
        event.setId(id);
        log.debug("Processing umltype for Event [{}]", id);
        event.setUmlType(element.getPlainAttribute("umltype"));

        log.info("Completed restructuring of Event [{}]", id);
        return event;
    }
}
