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

import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Specifies an interaction point between an {@link EncapsulatedClassifier}
 * and its environment.
 * See subclauses 11.3 and 11.8.14 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Port extends Property {
    /**
     * If this is {@code true} requests arriving at this port are sent to the
     * classifier behavior of the {@link EncapsulatedClassifier}.
     */
    private Boolean isBehavior = Boolean.FALSE;
    /**
     * Determines how {@link #required} and {@link #provided} are derived.
     * If this is {@code true} {@link #provided} is derived by collecting the
     * {@link Interface}s the port (and its supertypes) realize,
     * {@link #required} is derived by collecting the interfaces used by the
     * ports and its supertypes. If this is {@code false} the way of deriving
     * the fields is switched around.
     */
    private Boolean isConjugated = Boolean.FALSE;
    /**
     * If this is {@code true} this port is used to provide a part of the
     * functionality of the {@link EncapsulatedClassifier} to the rest of the
     * system.
     */
    private Boolean isService = Boolean.TRUE;
    /**
     * The interfaces specifying the functionality the
     * {@link EncapsulatedClassifier} offers to the rest of the system. The
     * attribute can be derived.
     */
    private List<Interface> required = new ArrayList<>();
    /**
     * The interfaces the {@link EncapsulatedClassifier} expects the rest of
     * the system to handle. This characterizes how the rest of the system
     * interacts with the port. The attribute can be derived.
     */
    private List<Interface> provided = new ArrayList<>();

    /**
     * Adds a new interface to {@link #provided}.
     *
     * @param anInterface The interface to add
     */
    public void addProvided(final Interface anInterface) {

        provided.add(anInterface);
    }

    /**
     * Adds a new interface to {@link #required}.
     *
     * @param anInterface The interface to add
     */
    public void addRequired(final Interface anInterface) {

        required.add(anInterface);
    }
}
