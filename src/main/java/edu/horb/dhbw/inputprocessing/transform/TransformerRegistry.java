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
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Enumeration;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.PrimitiveType;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.util.Caching;
import lombok.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TransformerRegistry implements ITransformerRegistry {
    /**
     * The map storing all the {@link ITransformer}s this registry knows.
     */
    private final Map<Class<? extends XMIElement>, ITransformer<?
            extends XMIElement, ? extends OOBase>>
            registry = new HashMap<>();

    @Override
    public void readyForNextTransforming() {

        for (Map.Entry<Class<? extends XMIElement>, ITransformer<?
                extends XMIElement, ? extends OOBase>> entry : registry
                .entrySet()) {
            ITransformer<?, ?> value = entry.getValue();
            if (value instanceof Caching) {
                ((Caching) value).cleanCache();
            }
        }
    }

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
        registry.put(Type.class, new OOTypeTransformer(this));
        registry.put(Interface.class, new OOInterfaceTransformer(this));
        registry.put(PrimitiveType.class, new OOPrimitiveTransformer(this));
        registry.put(Enumeration.class, new OOEnumTransformer(this));
        registry.put(Parameter.class, new OOParameterTransformer(this));
        registry.put(StateMachine.class, new OOLogicTransformer(this));
        registry.put(Behavior.class, new BehaviorTransformer(this));
    }

    /**
     * Registers a new {@link ITransformer} in this registry.
     *
     * @param fClass      The class the transformer transforms from.
     * @param transformer The transformer to add.
     * @param <T>         A class implementing {@link XMIElement}.
     */
    public <T extends XMIElement> void register(final Class<T> fClass,
                                                final ITransformer<T, ?
                                                        extends OOBase> transformer) {

        registry.put(fClass, transformer);
    }

    /**
     * @param clazz The class to transform from.
     * @param <F>   The class to transform from. Must implement
     *              {@link XMIElement}.
     * @param <T>   The class to transform to. Must be a subclass of
     *              {@link OOBase}.
     * @return An {@link ITransformer} capable of transforming a F to a T.
     */
    @NonNull
    public <F extends XMIElement, T extends OOBase> ITransformer<F, T> getTransformer(final Class<F> clazz) {

        return (ITransformer<F, T>) registry
                .getOrDefault(clazz, defaultTransformer);
    }

    /**
     * A default noop implementation of {@link ITransformer}.
     */
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

    @Override
    public <F extends XMIElement> void remove(final Class<F> fClass) {

        registry.remove(fClass);
    }
}
