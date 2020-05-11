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
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.statemachines.Region;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;

@Slf4j
public final class StateMachineRestructurer
        extends AbstractCachingRestructurer<StateMachine> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public StateMachineRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "statemachine");
    }

    @Override
    public StateMachine restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of StateMachine [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading StateMachine from cache",
                     id);
            return processed.get(id);
        }
        StateMachine machine = new StateMachine();
        processed.putIfAbsent(id, machine);
        machine.setId(id);

        log.debug("Processing umltype for StateMachine [{}]", id);
        machine.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing name for StateMachine [{}]", id);
        String name = element.getPlainAttribute("name");
        machine.setName(name);

        log.debug("Processing regions for StateMachine [{}]", id);
        Collection<ModelElement> regions =
                (Collection<ModelElement>) element.getSetAttribute("regions");
        machine.setRegion(delegateMany(regions, Region.class));
        machine.getRegion().forEach(r -> r.setStateMachine(machine));

        log.debug("Processing reentrant for StateMachine [{}]", id);
        String reentrant = element.getPlainAttribute("reentrant");
        Boolean isReentrant = StringUtils.isEmpty(reentrant) ? Boolean.TRUE
                                                             : Boolean
                                      .valueOf(reentrant);
        machine.setIsReentrant(isReentrant);

        log.debug("Processing parameters for StateMachine [{}]", id);
        Collection<ModelElement> parameters = (Collection<ModelElement>) element
                .getSetAttribute("parameters");
        machine.setOwnedParameter(delegateMany(parameters, Parameter.class));

        log.debug("Processing post for StateMachine [{}]", id);
        Collection<ModelElement> post =
                (Collection<ModelElement>) element.getSetAttribute("post");
        machine.setPostcondition(delegateMany(post, Constraint.class));

        log.debug("Processing pre for StateMachine [{}]", id);
        Collection<ModelElement> pre =
                (Collection<ModelElement>) element.getSetAttribute("pre");
        machine.setPrecondition(delegateMany(pre, Constraint.class));

        log.debug("Processing specification for StateMachine [{}]", id);
        ModelElement specification = element.getRefAttribute("specification");
        machine.setSpecification(
                delegateRestructuring(specification, Operation.class));
        if (machine.getSpecification() != null) {
            machine.getSpecification().getMethod().add(machine);
        }
        log.info("Completed restructuring of StateMachine [{}]", id);
        return machine;
    }
}
