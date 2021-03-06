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

import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Specifies an unlimited natural value.
 * See subclauses 8.2 and 8.6.14 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public final class LiteralUnlimitedNatural
        extends LiteralSpecificationImpl<UnlimitedNatural> {
    /**
     * The specified value.
     */
    private final UnlimitedNatural value;

    @Override
    public String toString() {

        return value.toString();
    }
}
