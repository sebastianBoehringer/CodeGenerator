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

package edu.horb.dhbw.datacore.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Value
public final class TransformedStereotype extends OOBase {

    /**
     * The elements this stereotype has been applied to.
     */
    private List<OOBase> targets = new ArrayList<>();

    /**
     * Attributes in this map are characterized in the metamodel by {@code
     * type = "data"} and {@code multiplicity = "one"}.
     */
    private Map<String, String> singlePlain = new HashMap<>();

    /**
     * Attributes in this map are characterized in the metamodel by {@code
     * type = "data"} and {@code multiplicity = "many"}.
     */
    private Map<String, Collection<String>> multiPlains = new HashMap<>();

    /**
     * Attributes in this map are characterized in the metamodel by {@code
     * type = "ref"} and {@code multiplicity = "one"}.
     */
    private Map<String, OOBase> singleRef = new HashMap<>();
    /**
     * Attributes in this map are characterized in the metamodel by {@code
     * type = "ref"} and {@code multiplicity = "many"}.
     */
    private Map<String, List<OOBase>> multiRefs = new HashMap<>();

    /**
     * The value used for the name attribute in the metamodel file.
     */
    private String type;

    /**
     * Throws an {@link UnsupportedOperationException}.
     */
    @Override
    protected OOBase getParent() {

        throw new UnsupportedOperationException(
                "Cannot call #getParent on a TransformedStereotype");
    }

    /**
     * Throws an {@link UnsupportedOperationException}.
     */
    @Override
    public String getFQName() {

        throw new UnsupportedOperationException(
                "Cannot call #getFQName on a TransformedStereotype");
    }

    /**
     * Returns the value of the attribute identified by the given name.
     * Convenience method that checks all maps in this class for the given
     * key and returns the first value found.
     * Search order is {@link #singlePlain} -> {@link #singleRef} ->
     * {@link #multiPlains} -> {@link #multiRefs}.
     *
     * @param attributeName The name of the attribute to return
     * @return The value associated with the given string or null if no map
     * contains the key
     */
    public Object getAttribute(final String attributeName) {

        if (singlePlain.containsKey(attributeName)) {
            return singlePlain.get(attributeName);
        }
        if (singleRef.containsKey(attributeName)) {
            return singleRef.get(attributeName);
        }
        if (multiPlains.containsKey(attributeName)) {
            return multiPlains.get(attributeName);
        }
        if (multiRefs.containsKey(attributeName)) {
            return multiRefs.get(attributeName);
        }
        return null;
    }
}
