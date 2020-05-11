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
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.commonstructure.ConstraintImpl;
import edu.horb.dhbw.datacore.uml.commonstructure.Element;
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public final class ConstraintRestrucuturer
        extends AbstractCachingRestructurer<Constraint> {
    /**
     * The name of the metamodel element this restructurer processes.
     */
    private static final String PROCESSED_METAMODEL_ELEMENT = "constraint";


    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ConstraintRestrucuturer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, PROCESSED_METAMODEL_ELEMENT);
    }

    @Override
    public Constraint restructure(@NonNull final ModelElement element) {

        String umlType = XMIUtil.getUMLType(element);
        if (!PROCESSED_METAMODEL_ELEMENT.equals(umlType)) {
            log.debug("Trying to delegate from Constraint to specialized type "
                              + "for [{}]", umlType);
            Class<? extends Constraint> aClass =
                    LookupUtil.constraintFromUMLType(umlType);
            if (aClass == null) {
                log.warn("Did not find matching class for [{}], restructuring "
                                 + "as Constraint", umlType);
            } else {
                return delegateRestructuring(element, aClass);
            }
        }

        String id = element.getXMIID();
        log.info("Beginning restructuring of Constraint [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading Constraint from cache",
                     id);
            return processed.get(id);
        }
        Constraint constraint = new ConstraintImpl();
        constraint.setId(id);
        processed.putIfAbsent(id, constraint);

        log.debug("Processing umltype for Constraint [{}]", id);
        constraint.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing specification for Constraint [{}]", id);
        ModelElement specification = element.getRefAttribute("specification");
        constraint.setSpecification(
                delegateRestructuring(specification, ValueSpecification.class));

        log.debug("Processing context for Constraint [{}]", id);
        ModelElement context = element.getRefAttribute("context");
        constraint.setContext(delegateRestructuring(context, Namespace.class));

        log.debug("Processing constrainedElement for Constraint [{}]", id);
        Collection<ModelElement> constrained =
                (Collection<ModelElement>) element
                        .getSetAttribute("constrainedElement");
        constraint.setConstrainedElement(
                delegateMany(constrained, Element.class));

        log.info("Completed restructuring of Constraint [{}]", id);
        return constraint;
    }
}
