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

package edu.horb.dhbw.datacore.uml.commonstructure;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * An element that has a type specified for it.
 * <br/>
 * See subclauses 7.5 and 7.8.22 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class TypedElement extends NamedElement {

    /**
     * The type of this element.
     */
    private Type type;
}
