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

import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.XMIElement;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * The default implementation for an {@link AbstractRestructurer}.
 * None of the methods actually process the data in any sort or way. This
 * will allow {@link IRestructurerMediator} to always provide an
 * {@link AbstractRestructurer} that can handle the given type.
 */
public final class NoopRestructurer extends AbstractRestructurer<XMIElement> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public NoopRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "Irrelevant");
    }

    /**
     * @param model The model to restructure
     * @return An empty list in every case
     */
    @Override
    public @NonNull List<XMIElement> restructure(@NonNull final Model model) {

        return Collections.emptyList();
    }

    /**
     * @param base    The object to add the general attributes to.
     * @param element The modelelement holding the information
     * @param <S>     A subclass of {@link XMIElement}.
     * @return Will always return base unchanged.
     */
    @Override
    public <S extends XMIElement> S restructure(final @NonNull S base,
                                                final @NonNull ModelElement element) {

        return base;
    }

    /**
     * @param element The modelElement to restructure
     * @return Always {@code null}
     */
    @Override
    public XMIElement restructure(@NonNull final ModelElement element) {

        return null;
    }
}
