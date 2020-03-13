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

package edu.horb.dhbw.datacore.model;

import edu.horb.dhbw.util.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OOPackage extends OOBase {

    /**
     * The elements contained in the package.
     */
    private List<OOType> containedElements = Collections.emptyList();

    /**
     * The package containing this package.
     */
    private OOPackage container = null;

    /**
     * Returns the fully qualified name of this type.
     * {@link Config#language} is used so that the delimiter can be
     * customized.
     *
     * @return The fully qualified name of this type
     */
    public String getFQName() {

        return container == null ? this.getName()
                                 : container.getFQName() + Config.CONFIG
                                         .getLanguage().getPackageNameLimiter()
                       + this.getName();
    }
}
