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

package edu.horb.dhbw.restructure.statemachines;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.enums.PseudostateKind;
import edu.horb.dhbw.datacore.uml.statemachines.Region;
import edu.horb.dhbw.datacore.uml.statemachines.State;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public final class StateRestructurer extends RestructurerBase<State> {
    /**
     * A map holding all the {@link State}s that have already been
     * processed. This maps from their xmi id to the actual instance.
     * The map is not synchronized, thus the class is most likely not
     * threadsafe.
     */
    private static final Map<String, State> ALREADY_PROCESSED = new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public StateRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "state");
    }

    @Override
    public State restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] in cache, loading state from cache", id);
            return ALREADY_PROCESSED.get(id);
        }
        State state = new State();
        ALREADY_PROCESSED.put(id, state);
        state.setId(id);

        log.info("Processing name for State [{}]", id);
        String name = element.getPlainAttribute("name");
        state.setName(name);

        log.info("Processing kind for State [{}]", id);
        String kind = element.getPlainAttribute("kind");
        PseudostateKind stateKind =
                StringUtils.isEmpty(kind) ? PseudostateKind.STATE
                                          : PseudostateKind.from(kind);
        state.setKind(stateKind);

        //Pseudo and FinalStates do not possess these attributes so we can
        // skip processing them if the state is not an actual state
        if (stateKind == PseudostateKind.STATE) {
            log.info("Processing regions for State [{}]", id);
            Collection<ModelElement> regions =
                    (Collection<ModelElement>) element
                            .getSetAttribute("regions");
            state.setRegion(delegateMany(regions, Region.class));

            log.info("Processing entry for State [{}]", id);
            ModelElement entry = element.getRefAttribute("entry");
            state.setEntry(delegateRestructuring(entry, Behavior.class));

            log.info("Processing exit for State [{}]", id);
            ModelElement exit = element.getRefAttribute("exit");
            state.setExit(delegateRestructuring(exit, Behavior.class));

            log.info("Processing doactivity for State [{}]", id);
            ModelElement doActivity = element.getRefAttribute("doactivity");
            state.setDoActivity(
                    delegateRestructuring(doActivity, Behavior.class));

            log.info("Processing conncetionpoints for State [{}]", id);
            Collection<ModelElement> connectionPoints =
                    (Collection<ModelElement>) element
                            .getSetAttribute("connectionpoints");
            state.setConnectionPoint(
                    delegateMany(connectionPoints, State.class));
        }
        return state;
    }

    @Override
    public void cleanCache() {

        ALREADY_PROCESSED.clear();
    }

    @Override
    public Optional<State> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
}
