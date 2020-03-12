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

import java.util.HashMap;
import java.util.Map;

public abstract class CachingTransformer<F extends XMIElement, T extends OOBase>
        extends BaseTransformer<F, T> {
    /**
     * @param registry The registry to use.
     */
    public CachingTransformer(final TransformerRegistry registry) {

        super(registry);
    }

    /**
     * The cache saving all the already processed {@link T}s.
     * This maps from the id
     */
    protected Map<String, T> cache = new HashMap<>();
}
