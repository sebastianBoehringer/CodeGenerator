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

package edu.horb.dhbw.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.packages.Stereotype;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class StereotypeRestructurer extends RestructurerBase<Stereotype> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public StereotypeRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "stereotype");
    }

    @Override
    public Stereotype restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        Stereotype stereotype = new Stereotype();
        stereotype.setId(id);

        log.info("Processing name for stereotype [{}]", id);
        String name = element.getPlainAttribute("name");
        stereotype.setName(name);

        return stereotype;
    }
}
