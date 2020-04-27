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

import edu.horb.dhbw.datacore.uml.enums.ParameterDirectionKind;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OOParameter extends OOBase {

    /**
     * The method that uses this parameter.
     */
    private OOMethod parent;
    /**
     * The type of this parameter.
     */
    private OOType type;
    /**
     * The default value of this parameter.
     */
    private String defaults;

    /**
     * The cardinality of the parameter.
     */
    private Cardinality cardinality;

    /**
     * If this parameter is headed into or out of a method.
     */
    private ParameterDirectionKind direction;
}
