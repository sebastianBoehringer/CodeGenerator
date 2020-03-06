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

import edu.horb.dhbw.datacore.uml.CommonElements;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public abstract class CachingRestructurer<T extends CommonElements>
        extends RestructurerBase<T> {

    /**
     * A mapping from the xmi:id of the element to the restructured object.
     */
    protected final Map<String, T> processed = new HashMap<>();

    public CachingRestructurer(final IRestructurerMediator iRestructurerMediator,
                               @NonNull final String type) {

        super(iRestructurerMediator, type);
    }

    /**
     * Cleans the cache of this restructurer.
     */
    public void cleanCache() {

        processed.clear();
    }
}
