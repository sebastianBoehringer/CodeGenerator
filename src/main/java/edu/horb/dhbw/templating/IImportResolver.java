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

package edu.horb.dhbw.templating;

import edu.horb.dhbw.datacore.model.Language;
import edu.horb.dhbw.datacore.model.OOType;

import java.util.Set;

public interface IImportResolver {

    /**
     * Resolves the import statements the source code needs so that it can be
     * compiled. This may differ from programming language to programming
     * language.
     *
     * @param lang The programming language in which the source code is
     *             generated
     * @param type The type whose imports should be resolved
     * @return The import statements needed to make the produced source code
     * compile.
     */
    Set<String> resolveImports(Language lang, OOType type);
}
