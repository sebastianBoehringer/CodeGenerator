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

package edu.horb.dhbw.datacore.uml;

import edu.horb.dhbw.datacore.uml.commonstructure.ElementImpl;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Value
public class AppliedStereotype extends ElementImpl {
    /**
     * The elements this stereotype has been applied to.
     */
    private final List<XMIElement> targets = new ArrayList<>();

    /**
     * Attributes in this map are characterized in the metamodel by {@code
     * type = "data"} and {@code multiplicity = "one"}.
     */
    private final Map<String, String> singlePlain = new HashMap<>();

    /**
     * Attributes in this map are characterized in the metamodel by {@code
     * type = "data"} and {@code multiplicity = "many"}.
     */
    private final Map<String, Collection<String>> multiPlains = new HashMap<>();

    /**
     * Attributes in this map are characterized in the metamodel by {@code
     * type = "ref"} and {@code multiplicity = "one"}.
     */
    private final Map<String, XMIElement> singleRef = new HashMap<>();
    /**
     * Attributes in this map are characterized in the metamodel by {@code
     * type = "ref"} and {@code multiplicity = "many"}.
     */
    private final Map<String, List<XMIElement>> multiRefs = new HashMap<>();
}
