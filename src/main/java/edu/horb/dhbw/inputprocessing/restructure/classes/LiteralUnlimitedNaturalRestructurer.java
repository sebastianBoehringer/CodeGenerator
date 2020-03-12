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
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.values.LiteralUnlimitedNatural;
import edu.horb.dhbw.inputprocessing.restructure.BaseRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class LiteralUnlimitedNaturalRestructurer
        extends BaseRestructurer<LiteralUnlimitedNatural> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public LiteralUnlimitedNaturalRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "literalunlimitednatural");
    }

    @Override
    public LiteralUnlimitedNatural restructure(
            @NonNull final ModelElement element) {

        String id = element.getXMIID();

        log.info("Processing value for LiteralUnlimitedNatural [{}]", id);
        String value = element.getPlainAttribute("value");
        LiteralUnlimitedNatural unlimitedNatural =
                new LiteralUnlimitedNatural(new UnlimitedNatural(value));

        unlimitedNatural.setId(id);

        return unlimitedNatural;
    }
}
