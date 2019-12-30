/*
 * Copyright (c) 2019 Sebastian Boehringer.
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
import lombok.NonNull;

import java.util.Collection;
import java.util.Optional;

public interface IRestructurer<T> {

    /**
     * Transforms (parts of) a model into a collection corresponding uml class
     * as found in the packages under edu.horb.dhbw.datacore.uml.*. Note that
     * this treats the model as an immutable object and does not change any
     * of its contents. So one can apply other IRestructurers to the same model.
     * The return value of this method will never be null.
     *
     * @param model The model to restructure
     * @return A collection with the restructured uml classes.
     */
    @NonNull Collection<T> restructure(@NonNull Model model);

    /**
     * Restructures a single element instead of the entire model.
     *
     * @param element The modelElement to restructure
     * @return A restrucutured uml class
     */
    T restructure(@NonNull ModelElement element);

    /**
     * Checks to see if the restructurer already processed a certain element.
     *
     * @param id The id of an element
     * @return {@code true} if the IRestructurer already processed the
     * instance, {@code false} otherwise
     */
    boolean wasProcessed(@NonNull String id);

    /**
     * Returns an element identified by the given id if the IRestructurer
     * already processed it.
     *
     * @param id The id of an element
     * @return An optional wrapping the element if it was already processed,
     * an empty Optional otherwise
     */
    Optional<T> getProcessed(@NonNull String id);
}
