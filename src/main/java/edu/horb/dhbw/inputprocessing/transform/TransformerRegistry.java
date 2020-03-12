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
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import lombok.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformerRegistry {
    /**
     * The map storing all the {@link ITransformer}s this registry knows.
     */
    private final Map<Class<? extends XMIElement>, ITransformer<?
            extends XMIElement, ? extends OOBase>>
            registry = new HashMap<>();

    /**
     * A noop {@link ITransformer} that is used when a specialized one is not
     * found.
     */
    private final DefaultTransformer defaultTransformer =
            new DefaultTransformer();

    /**
     * Default constructor.
     * Adds all transformers in the transfrom package to {@link #registry}.
     */
    public TransformerRegistry() {

        registry.put(UMLClass.class, new OOClassTransformer(this));
        registry.put(XMIElement.class, new DefaultTransformer());
        registry.put(Property.class, new OOFieldTransformer(this));
        registry.put(UMLPackage.class, new OOPackageTransformer(this));
        registry.put(Operation.class, new OOMethodTransformer(this));
    }

    /**
     * Registers a new {@link ITransformer} in this registry.
     *
     * @param key         The class the transformer transforms from.
     * @param transformer The transformer to add.
     * @param <T>         A class implementing {@link XMIElement}.
     */
    public <T extends XMIElement> void register(final Class<T> key,
                                                final ITransformer<T, ?
                                                        extends OOBase> transformer) {

        registry.put(key, transformer);
    }

    /**
     * @param clazz The class to transform from.
     * @param <F>   The class to transform from. Must implement
     *              {@link XMIElement}.
     * @param <T>   The class to transform to. Must be a subclass of
     *              {@link OOBase}.
     * @return An {@link ITransformer} capable of transforming a F to a T.
     */
    public <F extends XMIElement, T extends OOBase> ITransformer<F, T> getTransformer(final Class<F> clazz) {

        return (ITransformer<F, T>) registry
                .getOrDefault(clazz, defaultTransformer);
    }

    private static class DefaultTransformer
            implements ITransformer<XMIElement, OOBase> {
        @Override
        public @NonNull List<OOBase> transform(final @NonNull List<?> elements) {

            return Collections.emptyList();
        }

        @Override
        public OOBase transform(final @NonNull XMIElement element) {

            return null;
        }
    }
}
