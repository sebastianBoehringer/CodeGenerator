/*
 * Copyright (c) 2019 Sebastian Boehringer.
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

package edu.horb.dhbw.datacore.uml.packages;

import edu.horb.dhbw.datacore.uml.structuredclassifiers.Association;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Indicates that a metaclass is extended via a {@link Stereotype}.
 * See subclauses 12.3 and 12.4.1 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Extension extends Association {
    /**
     * If this is {@code true} an instance of this extension is created
     * whenever an instance of {@link #metaclass} is created. This attribute
     * can be derived
     */
    private Boolean isRequired = Boolean.FALSE;
    /**
     * The {@link UMLClass} that is extended by this extension. This attribute
     * can be derived.
     */
    private UMLClass metaclass;
    /**
     * The end of the extension that connects to a {@link Stereotype}.
     */
    private ExtensionEnd ownedExtensionEnd;

}
