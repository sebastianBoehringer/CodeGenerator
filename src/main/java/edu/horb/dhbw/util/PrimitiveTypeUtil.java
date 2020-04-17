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

package edu.horb.dhbw.util;


import edu.horb.dhbw.datacore.uml.primitivetypes.PrimitiveBoolean;
import edu.horb.dhbw.datacore.uml.primitivetypes.PrimitiveInteger;
import edu.horb.dhbw.datacore.uml.primitivetypes.PrimitiveReal;
import edu.horb.dhbw.datacore.uml.primitivetypes.PrimitiveString;
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.PrimitiveType;

public final class PrimitiveTypeUtil {

    private static final PrimitiveString primitiveString = new PrimitiveString();
    private static final PrimitiveBoolean primitiveBoolean = new PrimitiveBoolean();
    private static final PrimitiveInteger primitiveInteger = new PrimitiveInteger();
    private static final PrimitiveReal primitiveReal = new PrimitiveReal();
    private static final UnlimitedNatural unlimitedNatural = new UnlimitedNatural(0L);

    private PrimitiveTypeUtil() {

    }

    /**
     * @param uri The uri pointing to a primitive type
     * @return The object of the specified primitive type
     */
    public static PrimitiveType primitiveTypeFromURL(final String uri) {

        String reduced = uri.replaceAll(".*#", "").toLowerCase();
        switch (reduced) {
            case "string":
                return primitiveString;
            case "integer":
                return primitiveInteger;
            case "boolean":
                return primitiveBoolean;
            case "real":
                return primitiveReal;
            case "unlimitednatural":
                return unlimitedNatural;
            default:
                return null;
        }
    }
}
