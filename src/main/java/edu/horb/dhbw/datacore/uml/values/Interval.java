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

package edu.horb.dhbw.datacore.uml.values;

import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElementImpl;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Defines the range between to {@link ValueSpecification}s.
 * See subclauses 8.5 and 8.6.6 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Interval extends PackageableElementImpl
        implements ValueSpecification {
    /**
     * The maximum value of this interval.
     */
    private ValueSpecification max;
    /**
     * The minimum value of this interval.
     */
    private ValueSpecification min;

    private Type type;
}
