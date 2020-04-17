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
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.enums.TransitionKind;
import edu.horb.dhbw.datacore.uml.statemachines.Region;
import edu.horb.dhbw.datacore.uml.statemachines.State;
import edu.horb.dhbw.datacore.uml.statemachines.Transition;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

@Slf4j
public final class TransitionRestructurer
        extends AbstractCachingRestructurer<Transition> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public TransitionRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "transition");
    }

    @Override
    public Transition restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Transition [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found Transition id [{}] in cache", id);
            return processed.get(id);
        }
        Transition transition = new Transition();
        processed.put(id, transition);
        transition.setId(id);

        log.debug("Processing name for Transition [{}]", id);
        String name = element.getPlainAttribute("name");
        transition.setName(name);

        log.debug("Processing kind for Transition [{}]", id);
        String kind = element.getPlainAttribute("kind");
        TransitionKind transitionKind =
                StringUtils.isEmpty(kind) ? TransitionKind.EXTERNAL
                                          : TransitionKind.from(kind);
        transition.setKind(transitionKind);

        log.debug("Processing transsource for Transition [{}]", id);
        ModelElement source = element.getRefAttribute("transsource");
        transition.setSource(delegateRestructuring(source, State.class));
        transition.getSource().getOutgoing().add(transition);

        log.debug("Processing transtarget for Transition [{}]", id);
        ModelElement target = element.getRefAttribute("transtarget");
        transition.setTarget(delegateRestructuring(target, State.class));
        transition.getTarget().getIncoming().add(transition);

        log.debug("Processing guard for Transition [{}]", id);
        ModelElement guard = element.getRefAttribute("guard");
        if (guard != null) {
            transition.setGuard(delegateRestructuring(guard, LookupUtil
                    .constraintFromUMLType(XMIUtil.getUMLType(guard))));
        }
        log.debug("Processing effect for Transition [{}]", id);
        ModelElement effect = element.getRefAttribute("effect");
        transition.setEffect(delegateRestructuring(effect, Behavior.class));

        log.debug("Processing container for Transition [{}]", id);
        ModelElement container = element.getRefAttribute("container");
        transition.setContainer(delegateRestructuring(container, Region.class));

        log.info("Completed restructuring of Transition [{}]", id);
        return transition;
    }
}
