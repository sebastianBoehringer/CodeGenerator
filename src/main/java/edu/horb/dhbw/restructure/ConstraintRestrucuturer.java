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

package edu.horb.dhbw.restructure;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public final class ConstraintRestrucuturer
        extends RestructurerBase<Constraint> {

    /**
     * A map holding all the {@link Constraint}s that have already been
     * processed. This maps from their xmi id to the actual instance.
     * The map is not synchronized, thus the class is most likely not
     * threadsafe.
     */
    private static final Map<String, Constraint> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ConstraintRestrucuturer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "constraint");
    }

    @Override
    public Constraint restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] in cache, loading constraint from cache",
                     id);
            return ALREADY_PROCESSED.get(id);
        }
        Constraint constraint = new Constraint();

        constraint.setId(id);

        log.info("Processing specification for constraint [{}]", id);
        ModelElement specification = element.getRefAttribute("specification");
        constraint.setSpecification(
                delegateRestructuring(specification, ValueSpecification.class));

        log.info("Processing context for constraint [{}]", id);
        ModelElement context = element.getRefAttribute("context");
        constraint.setContext(delegateRestructuring(context, Namespace.class));

        log.debug("Saving constraint [{}] in cache", id);
        ALREADY_PROCESSED.put(id, constraint);
        return constraint;
    }

    @Override
    public Optional<Constraint> getProcessed(@NonNull final String id) {

        return Optional.empty();
    }
}
