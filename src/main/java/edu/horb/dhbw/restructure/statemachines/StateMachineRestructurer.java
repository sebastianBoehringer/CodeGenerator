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
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.statemachines.Region;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
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
public class StateMachineRestructurer extends RestructurerBase<StateMachine> {
    /**
     * A map holding all the {@link StateMachine}s that have already been
     * processed. This maps from their xmi id to the actual instance.
     * The map is not synchronized, thus the class is most likely not
     * threadsafe.
     */
    private static final Map<String, StateMachine> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public StateMachineRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "statemachine");
    }

    @Override
    public StateMachine restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] in cache, loading StateMachine from cache",
                     id);
            return ALREADY_PROCESSED.get(id);
        }
        StateMachine machine = new StateMachine();
        ALREADY_PROCESSED.put(id, machine);
        machine.setId(id);

        log.info("Processing name for StateMachine [{}]", id);
        String name = element.getPlainAttribute("name");
        machine.setName(name);

        log.info("Processing regions for StateMachine [{}]", id);
        Collection<ModelElement> regions =
                (Collection<ModelElement>) element.getSetAttribute("regions");
        machine.setRegion(delegateMany(regions, Region.class));

        log.info("Processing reentrant for StateMachine [{}]", id);
        String reentrant = element.getPlainAttribute("reentrant");
        Boolean isReentrant = StringUtils.isEmpty(reentrant) ? Boolean.TRUE
                                                             : Boolean
                                      .valueOf(reentrant);
        machine.setIsReentrant(isReentrant);

        log.info("Processing parameters for StateMachine [{}]", id);
        Collection<ModelElement> parameters = (Collection<ModelElement>) element
                .getSetAttribute("parameters");
        machine.setOwnedParameter(delegateMany(parameters, Parameter.class));

        log.info("Processing post for StateMachine [{}]", id);
        Collection<ModelElement> post =
                (Collection<ModelElement>) element.getSetAttribute("post");
        machine.setPostcondition(delegateMany(post, Constraint.class));

        log.info("Processing pre for StateMachine [{}]", id);
        Collection<ModelElement> pre =
                (Collection<ModelElement>) element.getSetAttribute("pre");
        machine.setPrecondition(delegateMany(pre, Constraint.class));

        log.info("Processing specification for StateMachine [{}]", id);
        ModelElement specification = element.getRefAttribute("specification");
        machine.setSpecification(
                delegateRestructuring(specification, Operation.class));

        return machine;
    }

    @Override
    public void cleanCache() {

        ALREADY_PROCESSED.clear();
    }

    @Override
    public Optional<StateMachine> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
}
