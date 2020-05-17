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
import edu.horb.dhbw.datacore.model.TemplatingOptions;
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
import java.io.FileInputStream;
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
     * Resolves the imports for a class file.
     */
    private IImportResolver importResolver;
    /**
     * The processor to use for reading the model file.
     */
    private IModelProcessor modelProcessor;
    /**
     * The adapter that is configured for the codegenerator.
     */
    private ITemplateEngineAdapter adapter;

    /**
     * Default constructor, that attempts to detect the configuration file
     * automatically.
     * <p>
     * It first searches for a file {@code "codegenerator.properties"} in the
     * current directory, after that it defaults to the default properties.
     * <p>
     * Using this constructor will result in using the default
     * {@link IModelProcessor}, i. e. {@link XMIModelProcessor} and the
     * default {@link IImportResolver}, i. e. {@link BasicImportResolver}. If
     * you wish to change either of these components use
     * {@link #CodeGenerator(ITemplateEngineAdapter, IImportResolver, IModelProcessor)}
     * instead.
     */
    public CodeGenerator() {

        if (Files.exists(Path.of("codegenerator.properties"))) {

            try {
                initFromInputStream(new BufferedInputStream(
                        new FileInputStream("codegenerator.properties")));
                return;
            } catch (FileNotFoundException e) {
                log.warn("Files.exists returned true but file was not found, "
                         + "trying other options");
            }
        }
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("codegenerator.properties");
        if (in != null) {
            this.initFromInputStream(in);
            return;

        }
        in = this.getClass().getClassLoader()
                .getResourceAsStream("default.properties");
        if (in != null) {
            initFromInputStream(in);
            return;
        }
        initFromInputStream(null);
    }


    private void initFromInputStream(final InputStream in) {

        Properties properties = new Properties();
        try {
            properties.load(in);
            Config.CONFIG.readInProperties(properties);
            log.info("Successfully read in properties");
        } catch (IOException | NullPointerException ex) {
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
        String adapterClass = Config.CONFIG.getLanguage().getTemplatingOptions()
                .getAdpaterClass();
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
     * Generates a codeGenerator using the .properties the path points to.
     * If the loading of the properties fails in any way, the default
     * properties will be used. See the implementation of
     * {@link Config#readInProperties(Properties)} for more details.
     *
     * @param propertyLocation The location of the properties file to use
     * @throws FileNotFoundException If the file specified by the path cannot
     *                               be found
     */
    public CodeGenerator(final Path propertyLocation)
            throws FileNotFoundException {

        InputStream in = new BufferedInputStream(
                new FileInputStream(propertyLocation.toFile()));
        initFromInputStream(in);
    }

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
        Config.CONFIG.readInProperties(new Properties());
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
        createOutputDirectory(language);
        for (OOPackage parsedPackage : modelProcessor.getParsedPackages()) {
            createPackageDirectory(parsedPackage, language);
        }
        TemplatingOptions options = language.getTemplatingOptions();

        adapter.initialize(language);
        for (OOType parsedClass : modelProcessor.getParsedClasses()) {
            log.info("Generating template for [{}]", parsedClass.getName());
            adapter.addToContext(options.getClassVariable(), parsedClass);
            addImports(parsedClass, language);
            adapter.process(options.getClassTemplateName(),
                            getFullPath(parsedClass, language));
        }
        for (OOType parsedEnum : modelProcessor.getParsedEnums()) {
            log.info("Generating template for [{}]", parsedEnum.getName());
            adapter.addToContext(options.getEnumVariable(), parsedEnum);
            addImports(parsedEnum, language);
            adapter.process(options.getEnumTemplateName(),
                            getFullPath(parsedEnum, language));
        }
        for (OOType parsedInterface : modelProcessor.getParsedInterfaces()) {
            log.info("Generating template for [{}]", parsedInterface.getName());
            adapter.addToContext(options.getInterfaceVariable(),
                                 parsedInterface);
            addImports(parsedInterface, language);
            adapter.process(options.getInterfaceTemplateName(),
                            getFullPath(parsedInterface, language));
        }
    }

    private void createOutputDirectory(final Language language)
            throws CodeGenerationException {

        if (!Files
                .exists(language.getTemplatingOptions().getOutputDirectory())) {
            try {
                Files.createDirectories(
                        language.getTemplatingOptions().getOutputDirectory());
            } catch (IOException e) {
                throw new CodeGenerationException(e);
            }
        }
    }

    /**
     * @param pkg      The package to create a directory for
     * @param language The language to generate code for
     * @throws CodeGenerationException IF the directory could not be created.
     *                                 Essentially a wrapper around
     *                                 {@link IOException} in this case
     */
    private void createPackageDirectory(final OOPackage pkg,
                                        final Language language)
            throws CodeGenerationException {

        String fqName = pkg.getFQName();
        String path =
                language.getTemplatingOptions().getOutputDirectory() + "\\"
                + fqNameToPath(fqName, language);
        Path output = Path.of(path);
        try {
            Files.createDirectories(output);
        } catch (IOException e) {
            throw new CodeGenerationException(
                    "Could not generate Code since output directory "
                    + "could not be generated. Nested " + "Exception is " + e
                            .getClass().getSimpleName() + ", cause: " + e
                            .getMessage());
        }

    }

    private void addImports(final OOType type, final Language language) {

        adapter.addToContext("imports",
                             importResolver.resolveImports(language, type));
    }

    private String getFullPath(final OOType type, final Language language) {

        return language.getTemplatingOptions().getOutputDirectory()
                       .toAbsolutePath().toString() + "\\" + fqNameToPath(
                type.getFQName(), language) + language.getExtension();
    }

    /**
     * @param fqName   The fully qualified name of either a {@link OOType} or a
     *                 {@link OOPackage}.
     * @param language The language to generate code for
     * @return The fqName but the {@link Language#packageNameLimiter} is
     * replaced by the path separator of the filesystem.
     */
    private String fqNameToPath(final String fqName, final Language language) {

        return fqName
                .replaceAll(Pattern.quote(language.getPackageNameLimiter()),
                            "/");
    }
}
