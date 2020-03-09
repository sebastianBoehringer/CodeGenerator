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

package edu.horb.dhbw.inputprocessing.restructure.delegating;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonstructure.TypedElement;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.inputprocessing.restructure.BaseRestructurer;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;

public final class TypedElementRestructurer
        extends BaseRestructurer<TypedElement> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public TypedElementRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "typedelement");
    }

    @Override
    public TypedElement restructure(@NonNull final ModelElement element) {

        Class<? extends TypedElement> clazz =
                LookupUtil.typedFromUMLType(XMIUtil.getUMLType(element));
        return delegateRestructuring(element, clazz);
    }
}
