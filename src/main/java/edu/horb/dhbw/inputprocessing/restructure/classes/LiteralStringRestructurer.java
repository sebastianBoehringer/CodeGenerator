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
import edu.horb.dhbw.datacore.uml.values.LiteralString;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.inputprocessing.restructure.BaseRestructurer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class LiteralStringRestructurer
        extends BaseRestructurer<LiteralString> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public LiteralStringRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "literalstring");
    }

    @Override
    public LiteralString restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();

        log.info("Processing value for LiteralString [{}]", id);
        String value = element.getPlainAttribute("value");
        LiteralString literalString = new LiteralString(value);

        literalString.setId(id);

        return literalString;
    }
}
