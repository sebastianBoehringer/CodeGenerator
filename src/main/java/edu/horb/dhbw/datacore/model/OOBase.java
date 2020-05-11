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

import edu.horb.dhbw.util.Config;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class OOBase {

    /**
     * The stereotypes applied to this element.
     */
    private List<TransformedStereotype> appliedStereotypes = new ArrayList<>();
    /**
     * The name of this OO-feature.
     */
    private String name;

    /**
     * An id which helps identify the OO-feature across multiple input files.
     */
    private String id;

    /**
     * Comments that were attached to the corresponding model element.
     */
    private List<String> comments;

    protected abstract OOBase getParent();

    /**
     * Returns the fully qualified name of this type.
     * {@link Config#language} is used so that the delimiter can be
     * customized.
     *
     * @return The fully qualified name of this type
     */
    public String getFQName() {

        return getParent() != null ? getParent().getFQName() + Config.CONFIG
                .getLanguage().getPackageNameLimiter() + name : name;
    }

    /**
     * Tests if a stereotype of the given name is applied to the element.
     * The stereotypeName must match the name of a metamodelelement declared
     * in the metamodel xml file.
     *
     * @param stereotypeName The name of the stereotype to check for
     * @return Whether a stereotype of that type has been applied to this
     * element
     *
     * @see TransformedStereotype#type
     */
    public boolean hasStereotypeApplied(final String stereotypeName) {

        return appliedStereotypes.stream()
                .anyMatch(s -> stereotypeName.equals(s.getType()));
    }

}
