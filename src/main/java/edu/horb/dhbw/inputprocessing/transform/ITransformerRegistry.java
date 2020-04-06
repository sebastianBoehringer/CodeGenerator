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

/**
 * A class that acts as a mediator for {@link ITransformer}s.
 * That way a transformer does only need to know a registry to access multiple
 */
public interface ITransformerRegistry {
    /**
     * Registers a new transformer with this registry.
     *
     * @param fClass      The class the transformer transforms from
     * @param transformer The transformer to register
     * @param <F>         The type the transformer transforms from
     */
    <F extends XMIElement> void register(Class<F> fClass, ITransformer<F, ?
            extends OOBase> transformer);

    /**
     * Removes the transformer transforming from the given type from the
     * registry.
     * Since {@link #getTransformer(Class)} only relies on the class a
     * {@link ITransformer} transforms from only one transformer can be
     * registered for each subtype {@link XMIElement}.
     *
     * @param fClass The class the transformer transforms from
     * @param <F>    The type the transformer transforms from
     */
    <F extends XMIElement> void remove(Class<F> fClass);

    /**
     * Returns the {@link ITransformer} for the appropriate pair of types.
     * The method itself does not provide type safety. It is only guaranteed
     * that the returned {@link ITransformer} transforms from the given class
     * but not to the wanted T. I. e. the user themselves has to keep track
     * of the concrete type of the registered {@link ITransformer}.
     * <p>
     * Since it is not allowed to return {@code null}, it is recommended to
     * return a default implementation like {@link NoopTransformer}.
     * This prevents NPEs from occurring in {@link ITransformer}s that rely on
     * others.
     *
     * @param clazz The clazz to transform from
     * @param <F>   The type to transform from
     * @param <T>   The type to transform to
     * @return The transformer registered for F via
     * {@link #register(Class, ITransformer)} or other means of an
     * implementing class
     */
    @NonNull <F extends XMIElement, T extends OOBase> ITransformer<F, T> getTransformer(Class<F> clazz);

    /**
     * Readies the mediator for the transformation of the next model.
     * {@link ITransformer}s may cache their result, e. g.
     * {@link CachingTransformer}. This is no problem if
     * the mediator is not used for transforming two models in a row. In
     * that case it might occur that the modelling tools have used the same
     * id for two different classes. Thus it is recommended for a caller to
     * call the method before a call to {@link #getTransformer(Class)} to ensure
     * correct results.
     * <p>
     * It is furthermore recommended that every {@link ITransformer}
     * that safes intermediate results and the like implements
     * {@link edu.horb.dhbw.util.Caching}.
     */
    void readyForNextTransforming();
}
