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

package edu.horb.dhbw.inputprocessing.restructure;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Element;
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.values.Interval;
import edu.horb.dhbw.datacore.uml.values.IntervalConstraint;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public final class IntervalConstraintRestrucuturer
        extends CachingRestructurer<IntervalConstraint> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public IntervalConstraintRestrucuturer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "intervalconstraint");
    }

    @Override
    public IntervalConstraint restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading intervalconstraint from "
                             + "cache", id);
            return processed.get(id);
        }
        IntervalConstraint constraint = new IntervalConstraint();
        constraint.setId(id);
        processed.put(id, constraint);

        log.info("Processing intervalspecification for intervalconstraint [{}]",
                 id);
        ModelElement specification =
                element.getRefAttribute("intervalspecification");
        constraint.setSpecification(
                delegateRestructuring(specification, Interval.class));

        log.info("Processing context for intervalconstraint [{}]", id);
        ModelElement context = element.getRefAttribute("context");
        constraint.setContext(delegateRestructuring(context, Namespace.class));

        log.info("Processing constrainedElement for intervalconstraint [{}]",
                 id);
        Collection<ModelElement> constrained =
                (Collection<ModelElement>) element
                        .getSetAttribute("constrainedElement");
        constraint.setConstrainedElement(
                delegateMany(constrained, Element.class));

        return constraint;
    }
}
