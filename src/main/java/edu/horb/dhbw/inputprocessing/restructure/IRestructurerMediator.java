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

public interface IRestructurerMediator extends IRestructurer<XMIElement> {

    /**
     * Adds the {@link IRestructurer} for the specified type to the mediator.
     * There can only be one {@link IRestructurer} registered for each
     * subtype of {@link XMIElement}. Thus invoking this method with two
     * different {@link IRestructurer}s for the same type will result in
     * removing the first from the mediator.
     *
     * @param clazz        The class for which a restructurer is added
     * @param restructurer The restructurer to add
     * @param <T>          Ensuring that the class and the restructurer match
     */
    <T extends XMIElement> void register(@NonNull Class<T> clazz,
                                         @NonNull IRestructurer<T> restructurer);

    /**
     * Removes the {@link IRestructurer} specified by the presented type from
     * the mediator.
     *
     * @param tClass The class the {@link IRestructurer} would restructure to
     * @param <T>    The type the {@link IRestructurer} would restructure to
     */
    <T extends XMIElement> void remove(@NonNull Class<T> tClass);

    /**
     * @param clazz The class to restructure to
     * @param <T>   The class that the IRestructurer restructures to. Upper
     *              bound is {@link XMIElement}.
     * @return An IRestructurer transforming a
     * {@link com.sdmetrics.model.Model} to a T
     */
    @NonNull <T extends XMIElement> IRestructurer<T> getIRestructurer(@NonNull Class<T> clazz);

    /**
     * {@inheritDoc}
     * This applies all the registered {@link IRestructurer} to the model, i.
     * e. it calls {@link IRestructurer#restructure(Model)} on every
     * registered restructurer.
     */
    @Override
    @NonNull List<XMIElement> restructure(@NonNull Model model);

    /**
     * {@inheritDoc}
     * Searches all registered {@link IRestructurer} and applies a valid one
     * to the passed element.
     * The {@link IRestructurer} to use can be determined by calling
     * {@link IRestructurer#canRestructure(ModelElement)}.
     */
    @Override
    XMIElement restructure(@NonNull ModelElement element);

    /**
     * Readies the mediator for the parsing of the next model.
     * {@link IRestructurer}s may cache their result, e. g.
     * {@link CachingRestructurer}. This is no problem if
     * the mediator is not used for restructuring two models in a row. In
     * that case it might occur that the modelling tools have used the same
     * id for two different classes. Thus it is recommended for a caller to
     * call the method before a call to {@link #restructure(Model)} to ensure
     * correct results.
     * <p>
     * It is furthermore recommended that every {@link IRestructurer}
     * that safes intermediate results and the like implements
     * {@link edu.horb.dhbw.util.Caching}.
     */
    void readyForNextRestructuring();
}
