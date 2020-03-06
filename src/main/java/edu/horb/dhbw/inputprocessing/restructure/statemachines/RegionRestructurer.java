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
import edu.horb.dhbw.datacore.uml.statemachines.Region;
import edu.horb.dhbw.datacore.uml.statemachines.State;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.datacore.uml.statemachines.Transition;
import edu.horb.dhbw.inputprocessing.restructure.CachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public final class RegionRestructurer extends CachingRestructurer<Region> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public RegionRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "region");
    }

    @Override
    public Region restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (processed.containsKey(id)) {
            log.info("Found Region id [{}] in cache", id);
            return processed.get(id);
        }
        Region region = new Region();
        processed.put(id, region);
        region.setId(id);

        log.info("Processing name for Region [{}]", id);
        String name = element.getPlainAttribute("name");
        region.setName(name);

        log.info("Processing subvertices for Region [{}]", id);
        Collection<ModelElement> vertices = (Collection<ModelElement>) element
                .getSetAttribute("subvertices");
        region.setSubvertex(delegateMany(vertices, State.class));

        log.info("Processing transitions for Region [{}]", id);
        Collection<ModelElement> transitions =
                (Collection<ModelElement>) element
                        .getSetAttribute("transitions");
        region.setTransition(delegateMany(transitions, Transition.class));

        log.info("Processing statemachine for Region [{}]", id);
        ModelElement stateMachine = element.getRefAttribute("statemachine");
        region.setStateMachine(
                delegateRestructuring(stateMachine, StateMachine.class));

        log.info("Processing state for Region [{}]", id);
        ModelElement state = element.getRefAttribute("state");
        region.setState(delegateRestructuring(state, State.class));

        return region;
    }
}
