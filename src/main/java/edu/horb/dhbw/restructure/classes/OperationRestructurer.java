/*
 * Copyright (c) 2019 Sebastian Boehringer.
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

package edu.horb.dhbw.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.enums.CallConcurrencyKind;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class OperationRestructurer extends RestructurerBase<Operation> {

    /**
     * A map holding all the {@link Operation}s that have already been
     * processed. This maps from their xmi id to the actual instance.
     * The map is not synchronized, thus the class is most likely not
     * threadsafe.
     */
    private static final Map<String, Operation> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public OperationRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "operation");
    }

    @Override
    public Optional<Operation> getProcessed(final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }

    @Override
    public Operation restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            return ALREADY_PROCESSED.get(id);
        }
        Operation operation = new Operation();

        operation.setId(id);

        String name = element.getPlainAttribute("name");
        operation.setName(name);

        //TODO context

        String isStatic = element.getPlainAttribute("static");
        operation.setIsStatic(Boolean.valueOf(isStatic));

        //there is no default visibility for operations in the uml
        // specification. Here, public is used
        String visibility = element.getPlainAttribute("visibility");
        VisibilityKind visibilityKind =
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility);
        operation.setVisibility(visibilityKind);

        String isAbstract = element.getPlainAttribute("abstract");
        operation.setIsAbstract(Boolean.valueOf(isAbstract));

        String query = element.getPlainAttribute("isquery");
        operation.setIsQuery(Boolean.valueOf(query));

        Collection<ModelElement> parameters = (Collection<ModelElement>) element
                .getSetAttribute("ownedparameters");
        operation.setOwnedParameter(delegateMany(parameters, Parameter.class));

        String concurrency = element.getPlainAttribute("concurrency");
        CallConcurrencyKind concurrencyKind =
                concurrency == null || "".equals(concurrency)
                ? CallConcurrencyKind.SEQUENTIAL
                : CallConcurrencyKind.from(concurrency);
        operation.setConcurrency(concurrencyKind);

        //TODO exceptions

        ALREADY_PROCESSED.put(operation.getId(), operation);
        return operation;
    }
}
