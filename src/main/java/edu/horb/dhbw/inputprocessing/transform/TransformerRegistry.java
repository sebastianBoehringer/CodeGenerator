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
import edu.horb.dhbw.datacore.uml.XMIElementImpl;
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
    private final Map<Class<? extends XMIElement>, ITransformer<?
            extends XMIElement, ? extends OOBase>>
            registry = new HashMap<>();

    private final DefaultTransformer defaultTransformer =
            new DefaultTransformer();

    public TransformerRegistry() {

        registry.put(UMLClass.class, new OOClassTransformer(this));
        registry.put(XMIElementImpl.class, new DefaultTransformer());
        registry.put(Property.class, new OOFieldTransformer(this));
        registry.put(UMLPackage.class, new OOPackageTransformer(this));
        registry.put(Operation.class, new OOMethodTransformer(this));
    }

    public <T extends XMIElementImpl> void addTransformer(final Class<T> key,
                                                          final ITransformer<T, ? extends OOBase> transformer) {

        registry.put(key, transformer);
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

    public <F extends XMIElement, T extends OOBase> ITransformer<F, T> getTransformer(final Class<F> clazz) {

        return (ITransformer<F, T>) registry
                .getOrDefault(clazz, defaultTransformer);
    }
}
