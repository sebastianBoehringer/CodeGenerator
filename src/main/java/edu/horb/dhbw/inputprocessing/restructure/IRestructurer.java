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

import java.util.List;

/**
 * An interface that provides the restructuring functionality.
 *
 * @param <T> The type of element this restructurer produces from a
 *            {@link ModelElement}
 */
public interface IRestructurer<T extends XMIElement> {

    /**
     * Transforms (parts of) a model into a collection corresponding uml class
     * as found in the packages under edu.horb.dhbw.datacore.uml.*. Note that
     * this treats the model as an immutable object and does not change any
     * of its contents. So one can apply other IRestructurers to the same model.
     * The return value of this method will never be null.
     *
     * @param model The model to restructure
     * @return A list with the restructured uml classes.
     */
    @NonNull List<T> restructure(@NonNull Model model);

    /**
     * Restructures a single element instead of the entire model.
     *
     * @param element The modelElement to restructure
     * @return A restrucutured uml class
     */
    T restructure(@NonNull ModelElement element);

    /**
     * Adds the elements specific to {@link T} to one of its subtypes {@link S}.
     *
     * @param base    The object to add the general attributes to.
     * @param element The modelelement holding the information
     * @param <S>     The subtype of {@link T} using some of its attributes.
     * @return base but the attributes of T were added to it
     */
    <S extends T> S restructure(@NonNull S base, @NonNull ModelElement element);

    /**
     * Checks to see, if the {@link IRestructurer} can handle the given
     * {@link ModelElement}.
     *
     * @param element The element to check
     * @return {@code True} if the {@link IRestructurer} can restructure this
     * element, {@code false} otherwise.
     */
    boolean canRestructure(@NonNull ModelElement element);
}
