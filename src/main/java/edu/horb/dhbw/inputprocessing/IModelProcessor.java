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

package edu.horb.dhbw.inputprocessing;

import edu.horb.dhbw.datacore.model.OOPackage;
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.exception.ModelParseException;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.List;

public interface IModelProcessor {

    /**
     * @param modelLocation A path locating where the model is stored
     * @throws ModelParseException If the parsing of the model at the given
     *                             location failed
     */
    void parseModel(@NonNull Path modelLocation)
            throws ModelParseException;

    /**
     * @return The classes the IModelProcessor parsed out of the given model.
     */
    @NonNull List<OOType> getParsedClasses();

    /**
     * @return The packages the IModelProcessor parsed out of the given model.
     */
    @NonNull List<OOPackage> getParsedPackages();

    /**
     * @return The interfaces the IModelProcessor parsed out of the given
     * model.
     */
    @NonNull List<OOType> getParsedInterfaces();
    /**
     * @return The enumerations the IModelProcessor parsed out of the given
     * model.
     */
    @NonNull List<OOType> getParsedEnums();

}
