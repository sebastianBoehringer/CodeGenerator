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

/**
 * A class representing a programming language.
 */
@Value
public class Language {
    /**
     * The name of the programming language.
     */
    private final String name;
    /**
     * The file extension used for the programming language.
     * This should include a leading dot ({@code '.'}).
     */
    private final String extension;

    /**
     * The string used to delimit the package name from the class name.
     * Java uses the {@code "."} for this purpose. C++ uses {@code "::"}.
     */
    private final String packageNameLimiter;

    /**
     * The location where templates for this language are stored.
     */
    private final Path templateLocation;

    /**
     * The scheme by which variables, files, etc. are named in this language.
     * E. g. for Java the names for classes are in camelcase and the file
     * names correspond to the class name.
     */
    private final INamingScheme scheme;

    /**
     * The string the language uses to mark something as public.
     */
    private final String publicVisibility;
    /**
     * The string the language uses to mark something as protected.
     */
    private final String protectedVisibility;
    /**
     * The string the language uses to mark something as package private.
     */
    private final String packageVisibility;
    /**
     * The string the language uses to mark something as private.
     */
    private final String privateVisibility;
    /**
     * The name of the class the language uses that corresponds to the
     * primitive UML type integer.
     */
    private final String integerName;
    /**
     * The name of the class the language uses that corresponds to the
     * primitive UML type string.
     */
    private final String stringName;
    /**
     * The name of the class the language uses that corresponds to the
     * primitive UML type boolean.
     */
    private final String booleanName;
    /**
     * The name of the class the language uses that corresponds to the
     * primitive UML type real.
     */
    private final String realName;
    /**
     * The name of the class the language uses that corresponds to the
     * primitive UML type unlimitedNatural.
     */
    private final String unlimitedNaturalName;
}
