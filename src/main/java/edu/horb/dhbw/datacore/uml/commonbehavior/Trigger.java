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

package edu.horb.dhbw.datacore.uml.commonbehavior;

import edu.horb.dhbw.datacore.uml.commonstructure.NamedElementImpl;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Port;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * A trigger describes a mechanism by which {@link Event}s can cause effects
 * in a {@link Behavior}.
 * See subclauses 13.3 and 13.4.11 of the UML specification for more details.
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public final class Trigger extends NamedElementImpl {
    /**
     * The event this trigger detects.
     */
    private Event event;

    /**
     * Optional ports through which the event is detected.
     */
    private List<Port> ports = new ArrayList<>();
}
