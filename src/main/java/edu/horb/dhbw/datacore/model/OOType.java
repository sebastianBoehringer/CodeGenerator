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

import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.util.Config;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OOType extends OOBase {

    /**
     * The kind of type this type represents. Accordingly some attributes may
     * be unused.
     */
    @Setter(AccessLevel.NONE)
    private final Type type;
    /**
     * The package containing this type.
     */
    private OOPackage container;
    /**
     * The visibility of the type inside that package.
     */
    private VisibilityKind visibility;
    /**
     * The methods defined in that type.
     */
    private List<OOMethod> methods = Collections.emptyList();
    /**
     * The literal values that are constants of this enum.
     * This will be an empty list if {@link #type} is anything other than
     * {@link Type#ENUMERATION}.
     */
    private List<String> literals = new ArrayList<>();
    /**
     * The types this type specializes.
     */
    private List<OOType> superTypes = Collections.emptyList();

    /**
     * Interfaces the class implements.
     * All types in this list must have a {@link #type} of
     * {@link Type#INTERFACE}.
     */
    private List<OOType> implementedInterfaces = Collections.emptyList();

    private boolean isAbstract;

    private boolean isFinal;

    private List<OOField> fields;

    public enum Type {
        /**
         * Designates this {@link OOType} as an Interface.
         */
        INTERFACE,
        /**
         * Designates this {@link OOType} as a Class.
         */
        CLASS,
        /**
         * Designates this {@link OOType} as an Enumeration.
         */
        ENUMERATION,
        /**
         * Designates this {@link OOType} as a primitive Type.
         */
        PRIMITIVE;
    }

    /**
     * Returns the fully qualified name of this type.
     * {@link Config#language} is used so that the delimiter can be
     * customized.
     *
     * @return The fully qualified name of this type
     */
    public String getFQName() {

        String delimiter = Config.CONFIG.getLanguage().getPackageNameLimiter();

        return container == null ? getName() : container.getFQName() + delimiter
                + getName();
    }

}
