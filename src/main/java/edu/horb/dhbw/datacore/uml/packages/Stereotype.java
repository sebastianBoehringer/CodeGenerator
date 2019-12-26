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

import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines how a metaclass may be extended. This is also the basis for
 * defining profiles so that one may use domain specific terminology.
 * See subclauses 12.3 and 12.4.9 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stereotype extends UMLClass {
    /**
     * Redefines the graphical appearance of the extended metaclass. Since
     * this does not hold any significance for codegeneration the field is
     * not expected to be used. Thus no add-shortcut is provided.
     */
    private List<Image> icon = new ArrayList<>();
    /**
     * The profile that contains this stereotype. This attribute can be derived.
     */
    private Profile profile;
}
