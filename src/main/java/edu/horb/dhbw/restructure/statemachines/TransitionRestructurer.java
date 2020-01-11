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
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.enums.TransitionKind;
import edu.horb.dhbw.datacore.uml.statemachines.Region;
import edu.horb.dhbw.datacore.uml.statemachines.State;
import edu.horb.dhbw.datacore.uml.statemachines.Transition;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class TransitionRestructurer extends RestructurerBase<Transition> {
    /**
     * A map holding all the {@link Transition}s that have already been
     * processed. This maps from their xmi id to the actual instance.
     * The map is not synchronized, thus the class is most likely not
     * threadsafe.
     */
    private static final Map<String, Transition> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public TransitionRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "transition");
    }

    @Override
    public Transition restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] in cache", id);
            return ALREADY_PROCESSED.get(id);
        }
        Transition transition = new Transition();
        ALREADY_PROCESSED.put(id, transition);
        transition.setId(id);

        log.info("Processing name for Transition [{}]", id);
        String name = element.getPlainAttribute("name");
        transition.setName(name);

        log.info("Processing kind for Transition [{}]", id);
        String kind = element.getPlainAttribute("kind");
        TransitionKind transitionKind =
                StringUtils.isEmpty(kind) ? TransitionKind.EXTERNAL
                                          : TransitionKind.from(kind);
        transition.setKind(transitionKind);

        log.info("Processing transsource for Transition [{}]", id);
        ModelElement source = element.getRefAttribute("transsource");
        transition.setSource(delegateRestructuring(source, State.class));

        log.info("Processing transtarget for Transition [{}]", id);
        ModelElement target = element.getRefAttribute("transtarget");
        transition.setTarget(delegateRestructuring(target, State.class));

        log.info("Processing guard for Transition [{}]", id);
        ModelElement guard = element.getRefAttribute("guard");
        transition.setGuard(delegateRestructuring(guard, Constraint.class));

        log.info("Processing effect for Transition [{}]", id);
        ModelElement effect = element.getRefAttribute("effect");
        transition.setEffect(delegateRestructuring(effect, Behavior.class));

        log.info("Processing container for Transition [{}]", id);
        ModelElement container = element.getRefAttribute("container");
        transition.setContainer(delegateRestructuring(container, Region.class));

        return transition;
    }

    @Override
    public void cleanCache() {

        ALREADY_PROCESSED.clear();
    }

    @Override
    public Optional<Transition> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
}
