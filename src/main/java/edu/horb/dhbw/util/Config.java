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

import edu.horb.dhbw.datacore.model.Language;
import edu.horb.dhbw.datacore.model.TemplatingOptions;
import edu.horb.dhbw.datacore.model.ValidationOptions;
import edu.horb.dhbw.inputprocessing.postvalidate.FirstLetter;
import lombok.Getter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Singleton that holds all the configuration for the entire application.
 */
@Getter
public enum Config {
    /**
     * A singleton holding the configuration for the codegenerator.
     */
    CONFIG();

    /**
     * Constructor that initializes the config with default.properties.
     */
    Config() {

        Properties props = new Properties();
        try (InputStream in = new BufferedInputStream(
                new FileInputStream("src/main/resources/default.properties"))) {
            props.load(in);
        } catch (IOException ex) {
            //ignored since the default properties should always exist.
            // Furthermore #readInProperties is coded defensively. Thus an
            // empty Properties object does no harm.
        }
        readInProperties(props);
    }


    /**
     * The path to the SDMetrics metamodel file.
     * Corresponds to property {@code xmi.sdmetrics.metamodel}.
     */
    private String metaModelPath;
    /**
     * The path to the SDMetrics transformations file.
     * Corresponds to property {@code xmi.sdmetrics.transformations}.
     */
    private String transformationsPath;

    /**
     * The language that is read in from the loaded properties.
     * Handles all of the properties with a prefix of {@code language}.
     */
    private Language language;

    /**
     * @param props The properties that should be used as a configuration
     */
    public void readInProperties(final Properties props) {

        metaModelPath = props.
                getProperty("xmi.sdmetrics.metamodel",
                            "src/main/resources/SDMetricsConfig"
                                    + "/metamodel2.xml");
        transformationsPath = props.
                getProperty("xmi.sdmetrics.transformations",
                            "src/main/resources/SDMetricsConfig"
                                    + "/xmiTrans2_0.xml");
        String publicVis =
                props.getProperty("language.visibility.public", "public");
        String protectedVis =
                props.getProperty("language.visibility.protected", "protected");
        String packageVis =
                props.getProperty("language.visibility.package", "");
        String privateVis =
                props.getProperty("language.visibility.private", "private");
        String primString =
                props.getProperty("language.primitive.string", "String");
        String primInt =
                props.getProperty("language.primitive.integer", "Integer");
        String primBool =
                props.getProperty("language.primitive.boolean", "Boolean");
        String primReal =
                props.getProperty("language.primitive.real", "Double");
        String primUnlimited =
                props.getProperty("language.primitive.unlimitedNatural",
                                  "Double");
        String extension = props.getProperty("language.extension", ".java");
        String delimiter =
                props.getProperty("language.formatting.packageDelimiter", ".");
        String name = props.getProperty("language.name", "Java");


        language = new Language(name, extension, delimiter, publicVis,
                                protectedVis, packageVis, privateVis, primInt,
                                primString, primBool, primReal, primUnlimited,
                                getValidationOptions(props),
                                getTemplatingOptions(props));

    }

    private ValidationOptions getValidationOptions(final Properties props) {

        String canImplementInterface =
                props.getProperty("validation.enums.canImplementInterface",
                                  "true");
        String enumMaxSuper =
                props.getProperty("validation.enums.maxSuper", "0");
        String interfaceMaxSuper =
                props.getProperty("validation.interfaces.maxSuper", "3333");
        String classesMaxSuper =
                props.getProperty("validation.classes.maxSuper", "1");
        String enumStart =
                props.getProperty("validation.enums.beginsWith", "UPPER");
        String classStart =
                props.getProperty("validation.classes.beginsWith", "UPPER");
        String interfaceStart =
                props.getProperty("validation.interfaces.beginsWith", "UPPER");
        String fieldStart =
                props.getProperty("validation.fields.beginsWith", "LOWER");
        String methodStart =
                props.getProperty("validation.methods.beginsWith", "LOWER");
        String pkgStart =
                props.getProperty("validation.packages.beginsWith", "LOWER");
        String paramStart =
                props.getProperty("validation.parameters.beginsWith", "LOWER");
        ValidationOptions options =
                new ValidationOptions(Integer.parseInt(enumMaxSuper),
                                      Boolean.parseBoolean(
                                              canImplementInterface),
                                      Integer.parseInt(classesMaxSuper),
                                      Integer.parseInt(interfaceMaxSuper));
        options.getFirstLetterMap()
                .put("enumeration", FirstLetter.from(enumStart));
        options.getFirstLetterMap().put("class", FirstLetter.from(classStart));
        options.getFirstLetterMap()
                .put("interface", FirstLetter.from(interfaceStart));
        options.getFirstLetterMap().put("field", FirstLetter.from(fieldStart));
        options.getFirstLetterMap()
                .put("method", FirstLetter.from(methodStart));
        options.getFirstLetterMap().put("package", FirstLetter.from(pkgStart));
        options.getFirstLetterMap()
                .put("parameter", FirstLetter.from(paramStart));
        return options;
    }

    private TemplatingOptions getTemplatingOptions(final Properties props) {

        String adpaterClass = props.
                getProperty("templating.adapter",
                            "edu.horb.dhbw.templating.ThymeleafAdapter");
        String templateLocation =
                props.getProperty("templating.template.location",
                                  "/src/resources/templates/");
        Path outputDirectory =
                Path.of(props.getProperty("templating.outputdirectory",
                                          "generated"));
        String className =
                props.getProperty("templating.template.class", "Class");
        String classVar =
                props.getProperty("templating.variable.class", "class");
        String interfaceName =
                props.getProperty("templating.template.interface", "Interface");
        String interfaceVar =
                props.getProperty("templating.variable.interface", "interface");
        String enumName = props.getProperty("templating.template.enumeration",
                                            "Enumeration");
        String enumVar = props.getProperty("templating.variable.enumeration",
                                           "enumeration");
        String templateExtension =
                props.getProperty("templating.template.extension", ".java");
        return new TemplatingOptions(adpaterClass, Path.of(templateLocation),
                                     outputDirectory, className, classVar,
                                     interfaceName, interfaceVar, enumName,
                                     enumVar, templateExtension);
    }
}
