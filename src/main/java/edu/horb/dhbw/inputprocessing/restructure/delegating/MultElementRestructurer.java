/*
 * Copyright (c) 2020 Sebastian Boehringer.
 *  This file is part of the CodeGenerator.
 *
 *  CodeGenerator is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * CodeGenerator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License along with CodeGenerator.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.horb.dhbw.inputprocessing.restructure.delegating;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonstructure.MultiplicityElement;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.inputprocessing.restructure.RestructurerBase;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;

public final class MultElementRestructurer
        extends RestructurerBase<MultiplicityElement> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public MultElementRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "multiplicityelement");
    }

    @Override
    public MultiplicityElement restructure(
            @NonNull final ModelElement element) {

        Class<? extends MultiplicityElement> clazz =
                LookupUtil.multiplicityFromUMLType(XMIUtil.getUMLType(element));
        return delegateRestructuring(element, clazz);
    }

}
