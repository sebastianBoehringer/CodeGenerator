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

import com.sdmetrics.model.ModelElement;
import lombok.NonNull;

public final class XMIUtil {

    private XMIUtil() {

    }

    /**
     * Determines the type of uml class that this element represents.
     *
     * @param element The element whose type should be determined
     * @return The type of the element with the namespace removed in all
     * lowercase
     */
    public static String getUMLType(@NonNull final ModelElement element) {

        String umlType = element.getPlainAttribute("umltype");
        return trimNamespace(umlType).toLowerCase();
    }

    /**
     * Removes the namespace {@code "uml:"} from a string.
     * E. g. an invocation on {@code "uml:example"} will return {@code
     * "example}. This usually should be the value of the attribute xmi:type.
     *
     * @param string The string where the namespace should be removed
     * @return The string with the namespace removed
     */
    public static String trimNamespace(@NonNull final String string) {

        return string.replaceFirst("uml:", "");
    }
}
