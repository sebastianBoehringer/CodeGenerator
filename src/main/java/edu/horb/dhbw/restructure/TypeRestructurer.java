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

package edu.horb.dhbw.restructure;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;

import java.util.Optional;

public final class TypeRestructurer extends RestructurerBase<Type> {
    /**
     * No op as this restructurer does not cache.
     */
    @Override
    public void cleanCache() {

    }

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public TypeRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "type");
    }

    @Override
    public Type restructure(@NonNull final ModelElement element) {

        return delegateRestructuring(element, LookupUtil
                .typeFromUMLType(XMIUtil.getUMLType(element)));
    }

    /**
     * @param id The id of an element
     * @return {@link Optional#EMPTY} as this restructurer does not cache
     */
    @Override
    public Optional<Type> getProcessed(@NonNull final String id) {

        return Optional.empty();
    }
}
