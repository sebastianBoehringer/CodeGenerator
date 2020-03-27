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

package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * A modular part of a system interacting with the other parts via its
 * required and provided interfaces.
 * See subclauses 11.6 and 11.8.6 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Component extends UMLClassImpl {
    /**
     * If this is {@code true} then this component is realized by just having
     * instances of its parts around, i. e. one does not need to initialize
     * the component itself.
     */
    private Boolean isIndirectlyInstantiated = Boolean.TRUE;
    /**
     * All model elements related to the definition of this component.
     */
    private List<PackageableElement> packagedElement = new ArrayList<>();
    /**
     * The set of interfaces this component provides to the rest of the
     * system. This attribute can be derived .
     */
    private List<Interface> provided = new ArrayList<>();
    /**
     * The elements realizing this component.
     */
    private List<ComponentRealization> realization = new ArrayList<>();
    /**
     * The set of interfaces this component requires to function. This
     * attribute can be derived.
     */
    private List<Interface> required = new ArrayList<>();

}
