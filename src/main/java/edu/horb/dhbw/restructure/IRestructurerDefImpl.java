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

import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Element;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * The default implementation for an {@link RestructurerBase}.
 * None of the methods actually process the data in any sort or way. This
 * will allow {@link IRestructurerMediator} to always provide an
 * {@link RestructurerBase} that can handle the given type.
 */
public final class IRestructurerDefImpl extends RestructurerBase<Element> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public IRestructurerDefImpl(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "Irrelevant");
    }

    /**
     * @param model The model to restructure
     * @return An empty list in every case
     */
    @Override
    public @NonNull List<Element> restructure(@NonNull final Model model) {

        return Collections.emptyList();
    }

    /**
     * @param element The modelElement to restructure
     * @return Always {@code null}
     */
    @Override
    public Element restructure(@NonNull final ModelElement element) {

        return null;
    }
}
