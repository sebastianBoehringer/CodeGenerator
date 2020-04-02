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

package edu.horb.dhbw;

import edu.horb.dhbw.datacore.model.Language;
import edu.horb.dhbw.datacore.model.OOPackage;
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.exception.CodeGenerationException;
import edu.horb.dhbw.exception.InvalidConfigurationException;
import edu.horb.dhbw.exception.ModelParseException;
import edu.horb.dhbw.exception.ModelValidationException;
import edu.horb.dhbw.inputprocessing.IModelProcessor;
import edu.horb.dhbw.inputprocessing.XMIModelProcessor;
import edu.horb.dhbw.inputprocessing.restructure.RestructurerMediator;
import edu.horb.dhbw.inputprocessing.transform.TransformerRegistry;
import edu.horb.dhbw.templating.BasicImportResolver;
import edu.horb.dhbw.templating.IImportResolver;
import edu.horb.dhbw.templating.ITemplateEngineAdapter;
import edu.horb.dhbw.util.Config;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Main class orchestrating the work of the different components.
 * Said components are the XMI-parsing, the validation of the input, the
 * transformation of the input into something useable and finally the
 * generation of source code by using a template engine.
 */
@Slf4j
public final class CodeGenerator {
    /**
     * The adapter that is configured for the codegenerator.
     */
    private ITemplateEngineAdapter adapter;

    /**
     * Resolves the imports for a class file.
     */
    private IImportResolver importResolver;

    /**
     * The processor to use for reading the model file.
     */
    private IModelProcessor modelProcessor;

    /**
     * @param engineAdapter The adapter to produce templates
     * @param resolver      A way to resolve the imports
     * @param processor     The processor to parse the model
     */
    public CodeGenerator(final ITemplateEngineAdapter engineAdapter,
                         final IImportResolver resolver,
                         final IModelProcessor processor) {

        this.adapter = engineAdapter;
        this.importResolver = resolver;
        this.modelProcessor = processor;
    }

    /**
     * Generates a codeGenerator using the .properties the path points to.
     * If the loading of the properties fails in any way, the default
     * properties will be used. See the implementation of
     * {@link Config#readInProperties(Properties)} for more details.
     *
     * @param propertyLocation The location of the properties file to use
     */
    public CodeGenerator(final Path propertyLocation) {

        Properties properties = new Properties();
        log.info("Trying to load properties from location [{}]",
                 propertyLocation.toAbsolutePath().toString());
        try (InputStream in = new BufferedInputStream(
                Files.newInputStream(propertyLocation))) {
            properties.load(in);
            Config.CONFIG.readInProperties(properties);
            log.info("Successfully read in properties");
        } catch (FileNotFoundException ex) {
            log.error("Did not find properties at location [{}]",
                      propertyLocation.toString());
            log.warn("Using default properties");
            Config.CONFIG.readInProperties(new Properties());
        } catch (IOException ex) {
            log.error("Something went wrong, exception message [{}]",
                      ex.getMessage());
            log.warn("Using default properties");
            Config.CONFIG.readInProperties(new Properties());
        }
        importResolver = new BasicImportResolver();
        setUpAdapter();
        modelProcessor = new XMIModelProcessor(new RestructurerMediator(),
                                               new TransformerRegistry(),
                                               Config.CONFIG.getLanguage()
                                                       .getValidationOptions());
    }

    private void setUpAdapter() {

        log.info("Setting up adapter");
        String adapterClass = Config.CONFIG.getAdpaterClass();
        try {
            Class<?> clazz = Class.forName(adapterClass);
            if (ITemplateEngineAdapter.class.isAssignableFrom(clazz)) {
                adapter = (ITemplateEngineAdapter) clazz.getConstructor()
                        .newInstance();
                adapter.initialize(Config.CONFIG.getLanguage());
            } else {
                log.error("[{}] does not implement ITemplateEngineAdapter, ",
                          adapterClass);
                throw new InvalidConfigurationException();
            }
        } catch (ClassNotFoundException e) {
            log.error("[{}] was not found by the current classloader",
                      adapterClass);
            throw new InvalidConfigurationException(
                    adapterClass + " was not " + "found");
        } catch (InstantiationException e) {
            log.error("Could not instantiate class [{}]", adapterClass);
            throw new InvalidConfigurationException(
                    "Could not instanciate " + adapterClass);
        } catch (InvocationTargetException e) {
            log.error("Constructor of class [{}] threw an exception with "
                              + "message [{}]", adapterClass, e.getMessage());
        } catch (NoSuchMethodException e) {
            log.error("[{}] does not offer a parameterless default "
                              + "constructor", adapterClass);
            throw new InvalidConfigurationException(
                    adapterClass + " does not offer a default constructor");
        } catch (IllegalAccessException e) {
            log.error("Could not access constructor of [{}], it might not be "
                              + "public", adapterClass);
            throw new InvalidConfigurationException(
                    "Could not access constructor of " + adapterClass);
        }
    }

    /**
     * Reads in the XMI and produces code for the specified language.
     * This method relies on {@link Config#CONFIG} having read in a
     * {@link Language}. Considering the singleton is initialized defensively
     * there will always be a language present, i. e. the standard language
     * Java.
     *
     * @param modelFile The file holding a valid representation of a model
     * @throws CodeGenerationException If the generation of code failed
     */
    public void generateCode(final Path modelFile)
            throws CodeGenerationException {

        generateCode(modelFile, Config.CONFIG.getLanguage());
    }

    /**
     * Reads in the XMI and produces code for the specified language.
     *
     * @param modelFile The file holding a valid representation of a model
     * @param language  The language the generated code should be in
     * @throws CodeGenerationException If the generation of code failed
     */
    public void generateCode(final Path modelFile, final Language language)
            throws CodeGenerationException {

        modelProcessor.initialize(language);
        try {
            modelProcessor.parseModel(modelFile);
        } catch (ModelParseException e) {
            throw new CodeGenerationException(
                    "Could not parse model, nested exception is " + e.getClass()
                            .getSimpleName() + ", message: " + e.getMessage());
        } catch (ModelValidationException e) {
            throw new CodeGenerationException(e);
        }
        for (OOPackage parsedPackage : modelProcessor.getParsedPackages()) {
            createPackageDirectory(parsedPackage);
        }

        adapter.initialize(language);
        for (OOType parsedClass : modelProcessor.getParsedClasses()) {
            log.info("Generating template for [{}]", parsedClass.getName());
            adapter.addToContext("class", parsedClass);
            addImports(parsedClass);
            adapter.process("Class", fqNameToPath(parsedClass.getFQName()));
        }
        for (OOType parsedEnum : modelProcessor.getParsedEnums()) {
            log.info("Generating template for [{}]", parsedEnum.getName());
            adapter.addToContext("enumeration", parsedEnum);
            addImports(parsedEnum);
            adapter.process("Enumeration",
                            fqNameToPath(parsedEnum.getFQName()));
        }
        for (OOType parsedInterface : modelProcessor.getParsedInterfaces()) {
            log.info("Generating template for [{}]", parsedInterface.getName());
            adapter.addToContext("interface", parsedInterface);
            addImports(parsedInterface);
            adapter.process("Interface",
                            fqNameToPath(parsedInterface.getFQName()));
        }
    }

    /**
     * @param pkg The package to create a directory for
     * @throws CodeGenerationException IF the directory could not be created.
     *                                 Essentially a wrapper around
     *                                 {@link IOException} in this case
     */
    private void createPackageDirectory(final OOPackage pkg)
            throws CodeGenerationException {

        String fqName = pkg.getFQName();
        String path =
                Config.CONFIG.getOutputDirectory() + "/" + fqNameToPath(fqName);
        Path output = Path.of(path);
        try {
            Files.createDirectories(output);
        } catch (IOException e) {
            throw new CodeGenerationException(
                    "Could not generate Code since output directory "
                            + "could not be generated. Nested "
                            + "Exception is " + e.getClass().getSimpleName()
                            + ", cause: " + e.getMessage());
        }

    }

    private void addImports(final OOType type) {

        adapter.addToContext("imports", importResolver
                .resolveImports(Config.CONFIG.getLanguage(), type));
    }

    /**
     * @param fqName The fully qualified name of either a {@link OOType} or a
     *               {@link OOPackage}.
     * @return The fqName but the {@link Language#packageNameLimiter} is
     * replaced by the path separator of the filesystem.
     */
    private String fqNameToPath(final String fqName) {

        return fqName.replaceAll(Pattern.quote(
                Config.CONFIG.getLanguage().getPackageNameLimiter()), "/");
    }
}
