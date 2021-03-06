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
import edu.horb.dhbw.datacore.uml.values.OpaqueExpression;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public final class OpaqueExpressionRestructurer
        extends AbstractRestructurer<OpaqueExpression> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public OpaqueExpressionRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "opaqueexpression");
    }

    @Override
    public OpaqueExpression restructure(final @NonNull ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of OpaqueExpression [{}]", id);
        OpaqueExpression expression = new OpaqueExpression();
        expression.setId(id);

        log.debug("Processing umltype for OpaqueExpression [{}]", id);
        expression.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing body for OpaqueExpression [{}]", id);
        Collection<String> body =
                (Collection<String>) element.getSetAttribute("body");
        expression.setBody(new ArrayList<>(body));

        log.debug("Processing language of OpaqueExpression [{}]", id);
        Collection<String> languages =
                (Collection<String>) element.getSetAttribute("language");
        expression.setLanguage(new ArrayList<>(languages));
        log.info("Completed restructuring of OpaqueExpression [{}]", id);
        return expression;
    }
}
