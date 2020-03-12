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
import edu.horb.dhbw.exception.CodeGenerationException;

/**
 * Adapter class so that the CodeGenerator can use any templateEngine the
 * user wants.
 * A default constructor is expected so that the instance can be created by
 * reflection.
 */
public interface ITemplateEngineAdapter {

    /**
     * Initializes the adapted templateEngine.
     * The method is supposed to be called prior to calls to
     * {@link #addToContext(String, Object)} and
     * {@link #process(String, String)}.
     *
     * @param language The language the templateEngine should generate.
     */
    void initialize(Language language);

    /**
     * Adds the given object to the templateContext.
     *
     * @param name The name the variable is referenced in the template
     * @param o    The value of the variable
     */
    void addToContext(String name, Object o);

    /**
     * Generates code by using the template identified by the given name.
     *
     * @param templateName The name of the template to process
     * @param fileName     The name of the file the template should result in
     *                     . This does not include the file extension. Using
     *                     a file extension might result in files being named
     *                     {@code "Example.java.java}.
     * @throws CodeGenerationException If the processing of the template
     *                                 could not complete
     */
    void process(String templateName, String fileName)
            throws CodeGenerationException;
}
