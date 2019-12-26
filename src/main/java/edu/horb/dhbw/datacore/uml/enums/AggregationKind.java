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
 * Specifies the aggregation of a
 * {@link edu.horb.dhbw.datacore.uml.classification.Property}.
 * See subclauses 9.5 and 9.9.1 of the UML specification for more details.
 */
public enum AggregationKind {
    /**
     * Indicates that the property is not aggregated.
     */
    NONE,
    /**
     * Indicated that the property is shared. This is a sort of middle ground
     * between the other two values. It only has varying meaning.
     */
    SHARED,
    /**
     * Indicates that the property is part of the composite. If the composite
     * is deleted so is the property.
     */
    COMPOSITE
}
