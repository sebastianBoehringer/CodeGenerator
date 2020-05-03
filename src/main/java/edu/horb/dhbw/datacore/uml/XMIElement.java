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

package edu.horb.dhbw.datacore.uml;

import java.net.URI;
import java.util.List;

public interface XMIElement {

    String getUmlType();

    void setUmlType(String umlType);

    String getId();

    void setId(String id);

    String getUuid();

    void setUuid(String uuid);

    String getLabel();

    void setLabel(String label);

    URI getHref();

    void setHref(URI href);

    String getIdref();

    void setIdref(String idref);

    /**
     * Applies the passed stereotype to the element.
     *
     * @param stereotype The stereotype to apply
     */
    void applyStereotype(AppliedStereotype stereotype);

    /**
     * @return The {@link AppliedStereotype} applied to this element.
     */
    List<AppliedStereotype> getAppliedStereotypes();
}
