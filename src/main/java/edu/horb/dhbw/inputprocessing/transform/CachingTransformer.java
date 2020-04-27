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
import edu.horb.dhbw.util.Caching;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public abstract class CachingTransformer<F extends XMIElement, T extends OOBase>
        extends AbstractTransformer<F, T> implements Caching {
    /**
     * The cache saving all the already processed {@link T}s.
     * This maps from the id
     */
    protected Map<String, T> cache = new HashMap<>();

    /**
     * @param registry The registry to use.
     */
    public CachingTransformer(final ITransformerRegistry registry) {

        super(registry);
    }

    /**
     * {@inheritDoc}
     * This checks the internal cache first. If the element is not found
     * there, the method delegates to {@link #doTransformation(XMIElement)}.
     *
     * @param element The single element to transform
     * @return The transformed element
     */
    @Override
    public final T transform(final @NonNull F element) {

        String id = element.getId();
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        return doTransformation(element);
    }

    protected abstract T doTransformation(@NonNull F element);

    @Override
    public void cleanCache() {

        cache.clear();
    }
}
