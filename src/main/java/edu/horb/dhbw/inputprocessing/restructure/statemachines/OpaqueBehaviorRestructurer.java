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
import edu.horb.dhbw.datacore.uml.commonbehavior.OpaqueBehavior;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.inputprocessing.restructure.AbstractRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public final class OpaqueBehaviorRestructurer
        extends AbstractRestructurer<OpaqueBehavior> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public OpaqueBehaviorRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "opaquebehavior");
    }

    @Override
    public OpaqueBehavior restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of OpaqueBehavior [{}]", id);
        OpaqueBehavior behavior = new OpaqueBehavior();
        behavior.setId(id);

        log.debug("Processing umltype for OpaqueBehavior [{}]", id);
        behavior.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing body for OpaqueBehavior [{}]", id);
        Collection<String> body =
                (Collection<String>) element.getSetAttribute("body");
        behavior.setBody(new ArrayList<>(body));

        log.debug("Processing language of OpaqueBehavior [{}]", id);
        Collection<String> languages =
                (Collection<String>) element.getSetAttribute("language");
        behavior.setLanguage(new ArrayList<>(languages));

        log.debug("Processing reentrant for OpaqueBehavior [{}]", id);
        String reentrant = element.getPlainAttribute("reentrant");
        Boolean isReentrant = StringUtils.isEmpty(reentrant) ? Boolean.TRUE
                                                             : Boolean
                                      .valueOf(reentrant);
        behavior.setIsReentrant(isReentrant);

        log.debug("Processing parameters for OpaqueBehavior [{}]", id);
        Collection<ModelElement> parameters = (Collection<ModelElement>) element
                .getSetAttribute("parameters");
        behavior.setOwnedParameter(delegateMany(parameters, Parameter.class));

        log.debug("Processing post for OpaqueBehavior [{}]", id);
        Collection<ModelElement> post =
                (Collection<ModelElement>) element.getSetAttribute("post");
        behavior.setPostcondition(delegateMany(post, Constraint.class));

        log.debug("Processing pre for OpaqueBehavior [{}]", id);
        Collection<ModelElement> pre =
                (Collection<ModelElement>) element.getSetAttribute("pre");
        behavior.setPrecondition(delegateMany(pre, Constraint.class));

        log.debug("Processing specification for OpaqueBehavior [{}]", id);
        ModelElement specification = element.getRefAttribute("specification");
        behavior.setSpecification(
                delegateRestructuring(specification, Operation.class));
        if (behavior.getSpecification() != null) {
            behavior.getSpecification().getMethod().add(behavior);
        }
        log.info("Completed restructuring of OpaqueBehavior [{}]", id);
        return behavior;
    }
}
