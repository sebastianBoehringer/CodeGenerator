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
 * Shows which {@link Profile}s have been applied to an {@link UMLPackage}.
 * See subclauses 12.3 and 12.4.8 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ProfileApplication extends DirectedRelationship {
    /**
     * If this is {@code true} the available metaclasses for the package must
     * be filtered. Thus only the metaclasses referenced by either
     * {@link Profile#metaclassReference} or
     * {@link Profile#metamodelReference} are available in the package.
     */
    private Boolean isStrict = Boolean.FALSE;
    /**
     * The profile that is applied to {@link #applyingPackage}.
     */
    private Profile appliedProfile;
    /**
     * The package that {@link #appliedProfile} is applied to.
     */
    private UMLPackage applyingPackage;
}
