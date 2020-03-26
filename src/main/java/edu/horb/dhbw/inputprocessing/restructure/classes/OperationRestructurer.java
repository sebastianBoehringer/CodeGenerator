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

package edu.horb.dhbw.inputprocessing.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.enums.CallConcurrencyKind;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.inputprocessing.restructure.CachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;

@Slf4j
public final class OperationRestructurer
        extends CachingRestructurer<Operation> {


    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public OperationRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "operation");
    }

    @Override
    public Operation restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading operation from cache",
                     id);
            return processed.get(id);
        }
        Operation operation = new Operation();
        operation.setId(id);
        processed.put(id, operation);

        log.info("Processing name for operation [{}]", id);
        String name = element.getPlainAttribute("name");
        operation.setName(name);

        log.info("Processing static for operation [{}]", id);
        String isStatic = element.getPlainAttribute("static");
        operation.setIsStatic(Boolean.valueOf(isStatic));

        //there is no default visibility for operations in the uml
        // specification. Here, public is used
        log.info("Processing visibility for operation [{}]", id);
        String visibility = element.getPlainAttribute("visibility");
        VisibilityKind visibilityKind =
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility);
        operation.setVisibility(visibilityKind);

        log.info("Processing abstract for operation [{}]", id);
        String isAbstract = element.getPlainAttribute("abstract");
        operation.setIsAbstract(Boolean.valueOf(isAbstract));

        log.info("Processing isquery for operation [{}]", id);
        String query = element.getPlainAttribute("isquery");
        operation.setIsQuery(Boolean.valueOf(query));

        log.info("Processing ownedparameters for operation [{}]", id);
        Collection<ModelElement> parameters = (Collection<ModelElement>) element
                .getSetAttribute("ownedparameters");
        operation.setOwnedParameter(delegateMany(parameters, Parameter.class));

        log.info("Processing concurrency for operation [{}]", id);
        String concurrency = element.getPlainAttribute("concurrency");
        CallConcurrencyKind concurrencyKind = StringUtils.isEmpty(concurrency)
                                              ? CallConcurrencyKind.SEQUENTIAL
                                              : CallConcurrencyKind
                                                      .from(concurrency);
        operation.setConcurrency(concurrencyKind);
        log.info("Processing exceptions for operation [{}]", id);
        Collection<ModelElement> exceptions = (Collection<ModelElement>) element
                .getSetAttribute("exceptions");
        operation.setRaisedException(delegateMany(exceptions, Type.class));

        log.info("Processing bodycondition for operation [{}]", id);
        ModelElement bodyCondition = element.getRefAttribute("bodycondition");
        operation.setBodyCondition(
                delegateRestructuring(bodyCondition, Constraint.class));

        return operation;
    }
}
