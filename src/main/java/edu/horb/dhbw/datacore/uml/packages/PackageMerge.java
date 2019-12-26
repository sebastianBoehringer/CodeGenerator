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

import edu.horb.dhbw.datacore.uml.commonstructure.DirectedRelationship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A special way to add a package to another one. The concepts for two
 * classifiers with the same name are merged, i. e. after the merge the
 * resulting classifier will share all of their attributes, etc.
 * See subclauses 12.2 and 12.4.6 of the UML specification for more details.
 * Subclauses 12.2.3.2 and onward explain the actual rules applied when
 * merging two packages.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class PackageMerge extends DirectedRelationship {
    /**
     * The package merged into {@link #receivingPackage}.
     */
    private UMLPackage mergedPackage;

    /**
     * The package {@link #mergedPackage} is merged into.
     */
    private UMLPackage receivingPackage;
}
