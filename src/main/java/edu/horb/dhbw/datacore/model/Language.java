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

/**
 * A class representing a programming language.
 */
@Value
public class Language {
    /**
     * The name of the programming language.
     * Corresponds to property {@code language.name}
     */
    private final String name;

    /**
     * The file extension used for the programming language.
     * This should include a leading dot ({@code '.'}).
     * Corresponds to property {@code language.extension}
     */
    private final String extension;

    /**
     * The string used to delimit the package name from the class name.
     * Java uses the {@code "."} for this purpose. C++ uses {@code "::"}.
     * Corresponds to property {@code language.formatting.packageDelimiter}.
     */
    private final String packageNameLimiter;

    /**
     * The string the language uses to mark something as public.
     * Corresponds to property {@code language.visibility.public}.
     */
    private final String publicVisibility;

    /**
     * The string the language uses to mark something as protected.
     * Corresponds to property {@code language.visibility.protected}.
     */
    private final String protectedVisibility;

    /**
     * The string the language uses to mark something as package private.
     * Corresponds to property {@code language.visibility.package}.
     */
    private final String packageVisibility;

    /**
     * The string the language uses to mark something as private.
     * Corresponds to property {@code language.visibility.private}.
     */
    private final String privateVisibility;

    /**
     * The name of the class the language uses that corresponds to the
     * primitive UML type integer.
     * Corresponds to property {@code language.primitive.integer}.
     */
    private final String integerName;

    /**
     * The name of the class the language uses that corresponds to the
     * primitive UML type string.
     * Corresponds to property {@code language.primitive.string}.
     */
    private final String stringName;

    /**
     * The name of the class the language uses that corresponds to the
     * primitive UML type boolean.
     * Corresponds to property {@code language.primitive.boolean}.
     */
    private final String booleanName;

    /**
     * The name of the class the language uses that corresponds to the
     * primitive UML type real.
     * Corresponds to property {@code language.primitive.real}.
     */
    private final String realName;

    /**
     * The name of the class the language uses that corresponds to the
     * primitive UML type unlimitedNatural.
     * Corresponds to property {@code language.primitive.unlimitedNatural}.
     */
    private final String unlimitedNaturalName;

    /**
     * The options used to configure the postvalidation of a
     * {@link edu.horb.dhbw.inputprocessing.XMIModelProcessor}.
     * Wraps properties whose key begins with {@code validation.}.
     */
    private final ValidationOptions validationOptions;
    /**
     * The options used to configure the
     * {@link edu.horb.dhbw.templating.ITemplateEngineAdapter} of the
     * {@link edu.horb.dhbw.CodeGenerator}.
     * Wraps properties whose key begins with {@code templating.}.
     */
    private final TemplatingOptions templatingOptions;
    /**
     * The options used to configure the
     * {@link edu.horb.dhbw.templating.BasicImportResolver}.
     * Wraps properties whose key begins with {@code language.imports.}.
     */
    private final ImportOptions importOptions;
}
