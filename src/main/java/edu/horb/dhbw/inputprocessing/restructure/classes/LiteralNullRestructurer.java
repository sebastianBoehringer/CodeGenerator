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
import edu.horb.dhbw.datacore.uml.values.LiteralNull;
import edu.horb.dhbw.inputprocessing.restructure.AbstractRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class LiteralNullRestructurer
        extends AbstractRestructurer<LiteralNull> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public LiteralNullRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "literalnull");
    }

    @Override
    public LiteralNull restructure(@NonNull final ModelElement element) {

        LiteralNull literalNull = new LiteralNull();
        String id = element.getXMIID();
        log.info("Beginning restructuring of LiteralNull [{}]", id);
        literalNull.setId(id);

        log.debug("Processing umltype for LiteralNull [{}]", id);
        literalNull.setUmlType(element.getPlainAttribute("umltype"));

        log.info("Completed restructuring of LiteralNull [{}]", id);
        return literalNull;
    }
}
