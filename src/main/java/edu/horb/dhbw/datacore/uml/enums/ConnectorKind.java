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

package edu.horb.dhbw.datacore.uml.enums;


/**
 * Defines the different kinds of
 * {@link edu.horb.dhbw.datacore.uml.structuredclassifiers.Connector}s.
 * See subclauses 11.2 and 11.8.12 of the UML specification for more details.
 */
public enum ConnectorKind {
    /**
     * A "normal" connector without any sort of additional semantics.
     */
    ASSEMBLY,
    /**
     * Messages incoming on the port this connector is attached to are
     * handled by the element on the end of the connector.
     */
    DELEGATION
}
