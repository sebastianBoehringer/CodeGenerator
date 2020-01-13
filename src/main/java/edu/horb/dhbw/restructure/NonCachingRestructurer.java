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

import edu.horb.dhbw.datacore.uml.CommonElements;
import lombok.NonNull;

import java.util.Optional;

/**
 * A restructurer that does not hold onto its results.
 * This can be used for simple classes like
 * {@link edu.horb.dhbw.datacore.uml.simpleclassifiers.EnumerationLiteral} or
 * any of the delegating restructurers, i. e. those that handle an abstract
 * class.
 *
 * @param <T> The type the restructurer produces from the
 *            {@link com.sdmetrics.model.ModelElement}.
 */
public abstract class NonCachingRestructurer<T extends CommonElements>
        extends RestructurerBase<T> {
    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     * @param type                  The name of the metamodel element this
     *                              restructurer handels.
     */
    public NonCachingRestructurer(final IRestructurerMediator iRestructurerMediator,
                                  @NonNull final String type) {

        super(iRestructurerMediator, type);
    }

    /**
     * No op as this restructurer does not cache.
     */
    @Override
    public void cleanCache() {

    }

    /**
     * @param id The id of an element
     * @return {@link Optional#EMPTY} as this restructurer does not cache
     */
    @Override
    public Optional<T> getProcessed(@NonNull final String id) {

        return Optional.empty();
    }

    /**
     * Checks to see if the element with the given id was processed by the
     * restructurer.
     * Since this restructurer does not cache, it does not know which
     * elements is processed and will always return {@code false}.
     *
     * @param id The id of an element
     * @return {@code false}
     */
    @Override
    public boolean wasProcessed(final String id) {

        return false;
    }
}
