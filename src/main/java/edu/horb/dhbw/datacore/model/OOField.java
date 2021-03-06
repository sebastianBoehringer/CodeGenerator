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
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OOField extends OOBase {

    /**
     * The type the field belongs to.
     */
    private OOType parent;
    /**
     * The type of this field.
     */
    private OOType type;

    /**
     * The visibility of the field.
     */
    private VisibilityKind visibility;

    /**
     * Represents the cardinality of the field.
     */
    private Cardinality cardinality;

    /**
     * If this is {@code true} the field cannot be written to.
     */
    private boolean readOnly;

    /**
     * If this is {@code true} the field is static.
     */
    private boolean isStatic;

    /**
     * A default value for this field.
     * Does not have to be set.
     */
    private String defaultValue;

}
