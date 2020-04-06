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

import lombok.Value;

import java.nio.file.Path;

@Value
public final class TemplatingOptions {
    /**
     * The fully qualified class name of a class implementing
     * {@link edu.horb.dhbw.templating.ITemplateEngineAdapter}.
     * Corresponds to property {@code templating.adapter}.
     */
    private String adpaterClass;
    /**
     * The location where templates for this language are stored.
     */
    private Path templateLocation;
    /**
     * The directory in which the code should be generated into.
     * Corresponds to property {@code templating.outputdirectory}.
     */
    private Path outputDirectory;
    /**
     * The name of the template to use for turning a class into sourcecode.
     * This does not include the file extension.
     * Corresponds to property {@code templating.template.class}.
     */
    private String classTemplateName;
    /**
     * The name of the variable under which the class is available in the
     * template.
     * Corresponds to property {@code templating.variable.class}.
     */
    private String classVariable;
    /**
     * The name of the template to use for turning a interface into sourcecode.
     * This does not include the file extension.
     * Corresponds to property {@code templating.template.interface}.
     */
    private String interfaceTemplateName;
    /**
     * The name of the variable under which the interface is available in the
     * template.
     * Corresponds to property {@code templating.variable.interface}.
     */
    private String interfaceVariable;
    /**
     * The name of the template to use for turning a enumeration into
     * sourcecode.
     * This does not include the file extension.
     * Corresponds to property {@code templating.template.enumeration}.
     */
    private String enumTemplateName;
    /**
     * The name of the variable under which the enumeration is available in the
     * template.
     * Corresponds to property {@code templating.variable.enumeration}.
     */
    private String enumVariable;
    /**
     * The extension for the templatefiles the configured
     * {@link edu.horb.dhbw.templating.ITemplateEngineAdapter} uses.
     * Corresponds to property {@code templating.template.extension}.
     * This should include a leading dot ({@code '.'}).
     */
    private String templateExtension;
}
