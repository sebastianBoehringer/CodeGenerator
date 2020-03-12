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

package edu.horb.dhbw.inputprocessing.transform;

import edu.horb.dhbw.datacore.model.OOBase;
import edu.horb.dhbw.datacore.uml.XMIElement;
import lombok.NonNull;

import java.util.List;

/**
 * Transforms an {@link F} to a {@link T}.
 *
 * @param <F> The type to transform from
 * @param <T> The type to transform to
 */
public interface ITransformer<F extends XMIElement, T extends OOBase> {
    /**
     * Transforms all the elements in the given list that are a {@link F}.
     *
     * @param elements The list containing the elements to transform.
     * @return A list with all the transformed elements.
     */

    @NonNull List<T> transform(@NonNull List<?> elements);

    /**
     * @param element The single element to transform
     * @return Transforms the given element to an {@link T}
     */
    T transform(@NonNull F element);


}
