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

import com.sdmetrics.model.Model;
import edu.horb.dhbw.datacore.model.Language;
import edu.horb.dhbw.datacore.model.OOBase;
import edu.horb.dhbw.datacore.model.OOPackage;
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.model.TransformedStereotype;
import edu.horb.dhbw.datacore.model.ValidationOptions;
import edu.horb.dhbw.datacore.uml.AppliedStereotype;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImport;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Enumeration;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.exception.CodeGenerationException;
import edu.horb.dhbw.exception.ModelParseException;
import edu.horb.dhbw.exception.ModelValidationException;
import edu.horb.dhbw.inputprocessing.postvalidate.EnumValidator;
import edu.horb.dhbw.inputprocessing.postvalidate.FieldValidator;
import edu.horb.dhbw.inputprocessing.postvalidate.IPostValidator;
import edu.horb.dhbw.inputprocessing.postvalidate.MethodValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.IPreValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.classification.BehavioralFeatureValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.classification.ClassifierValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.classification.GeneralizationSetValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.classification.InstanceSpecificationValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.classification.OperationValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.classification.ParameterValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.classification.PropertyValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.commonbehavior.BehaviorValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.commonbehavior.FunctionBehaviorValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.commonbehavior.TriggerValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.commonstructure.ConstraintValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.commonstructure.ElementImportValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.commonstructure.ElementValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.commonstructure.MultiplicityElementValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.commonstructure.NamedElementValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.commonstructure.NamespaceValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.commonstructure.PackageImportValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.commonstructure.PackageableElementValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.packages.PackageValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.simpleclassifiers.BehavioredClassifierValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.simpleclassifiers.EnumerationValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.simpleclassifiers.InterfaceValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.statemachines.FinalStateValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.statemachines.PseudoStateValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.statemachines.RegionValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.statemachines.StateMachineValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.statemachines.StateValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.statemachines.TransitionValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.structuredclassifiers.AssociationClassValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.structuredclassifiers.AssociationValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.structuredclassifiers.ClassValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.structuredclassifiers.ComponentValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.structuredclassifiers.ConnectorEndValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.structuredclassifiers.ConnectorValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.structuredclassifiers.PortValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.values.OpaqueExpressionValidator;
import edu.horb.dhbw.inputprocessing.prevalidate.values.StringExpressionValidator;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.inputprocessing.restructure.RestructurerMediator;
import edu.horb.dhbw.inputprocessing.transform.ITransformer;
import edu.horb.dhbw.inputprocessing.transform.ITransformerRegistry;
import edu.horb.dhbw.inputprocessing.transform.OOBaseStringWrapper;
import edu.horb.dhbw.inputprocessing.transform.StateMachineTransformer;
import edu.horb.dhbw.inputprocessing.transform.TransformerRegistry;
import edu.horb.dhbw.util.SDMetricsUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Slf4j
public final class XMIModelProcessor implements IModelProcessor {
    /**
     * A cache for the classes that have been processed. Invoking
     * {@link #parseModel(Path)} cleans up the cache automatically.
     */
    private final List<OOType> parsedClasses = new ArrayList<>();
    /**
     * A cache for the interfaces that have been processed. Invoking
     * {@link #parseModel(Path)} cleans up the cache automatically.
     */
    private final List<OOType> parsedInterfaces = new ArrayList<>();
    /**
     * A cache for the packages that have been processed. Invoking
     * {@link #parseModel(Path)} cleans up the cache automatically.
     */
    private final List<OOPackage> parsedPackages = new ArrayList<>();
    /**
     * A cache for the enumeration that have been processed. Invoking
     * {@link #parseModel(Path)} cleans up the cache automatically.
     */
    private final List<OOType> parsedEnums = new ArrayList<>();
    /**
     * The {@link IPreValidator} the processor applies after restructuring
     * the xmi elements.
     */
    private final List<IPreValidator> preValidators = new ArrayList<>();
    /**
     * The {@link IPostValidator} the processor applies after transforming
     * the uml {@link edu.horb.dhbw.datacore.uml.commonstructure.Element}s.
     */
    private final List<IPostValidator> postValidators = new ArrayList<>();

    /**
     * The mediator used to restructure the parsed {@link Model}.
     */
    private final IRestructurerMediator mediator;

    /**
     * The registry used to transform the restructured classes.
     */
    private final ITransformerRegistry registry;

    /**
     * Constructs a XMIModelProcessor with a {@link RestructurerMediator} as
     * the used {@link IRestructurerMediator} and a
     * {@link TransformerRegistry} as the used {@link ITransformerRegistry}.
     * Both are using their default mappings.
     *
     * @param options The validation options to configure the
     *                {@link IPostValidator}
     */
    public XMIModelProcessor(final ValidationOptions options) {

        registry = new TransformerRegistry();
        mediator = new RestructurerMediator();
        initDefaultValidators(options);
    }

    private void initDefaultValidators(final ValidationOptions options) {

        preValidators.addAll(Arrays.asList(new ConstraintValidator(),
                                           new ElementImportValidator(),
                                           new ElementValidator(),
                                           new MultiplicityElementValidator(),
                                           new NamedElementValidator(),
                                           new NamespaceValidator(),
                                           new PackageableElementValidator(),
                                           new PackageImportValidator()));
        preValidators.addAll(Arrays.asList(new OpaqueExpressionValidator(),
                                           new StringExpressionValidator()));
        preValidators.addAll(Arrays.asList(new BehavioralFeatureValidator(),
                                           new ClassifierValidator(),
                                           new GeneralizationSetValidator(),
                                           new InstanceSpecificationValidator(),
                                           new OperationValidator(),
                                           new ParameterValidator(),
                                           new PropertyValidator()));
        preValidators.addAll(Arrays.asList(new BehavioredClassifierValidator(),
                                           new EnumerationValidator(),
                                           new InterfaceValidator()));
        preValidators.addAll(Arrays.asList(new AssociationClassValidator(),
                                           new AssociationValidator(),
                                           new ClassValidator(),
                                           new ComponentValidator(),
                                           new ConnectorEndValidator(),
                                           new ConnectorValidator(),
                                           new PortValidator()));
        preValidators.add(new PackageValidator());
        preValidators.addAll(Arrays.asList(new FunctionBehaviorValidator(),
                                           new BehaviorValidator(),
                                           new TriggerValidator()));
        preValidators.addAll(Arrays.asList(new FinalStateValidator(),
                                           new PseudoStateValidator(),
                                           new RegionValidator(),
                                           new StateMachineValidator(),
                                           new StateValidator(),
                                           new TransitionValidator()));

        configurePostValidators(options);
    }

    private void configurePostValidators(final ValidationOptions options) {

        postValidators.addAll(Arrays.asList(
                new edu.horb.dhbw.inputprocessing.postvalidate.ClassValidator(
                        options.getClassesMaxSuper(),
                        options.getFirstLetterMap().get("class")),
                new EnumValidator(options.getEnumsMaxSuper(),
                                  options.getFirstLetterMap()
                                          .get("enumeration"),
                                  options.isEnumCanImplementInterface()),
                new FieldValidator(options.getFirstLetterMap().get("field")),
                new edu.horb.dhbw.inputprocessing.postvalidate.InterfaceValidator(
                        options.getInterfacesMaxSuper(),
                        options.getFirstLetterMap().get("interface")),
                new MethodValidator(options.getFirstLetterMap().get("method")),
                new edu.horb.dhbw.inputprocessing.postvalidate.PackageValidator(
                        options.getFirstLetterMap().get("package")),
                new edu.horb.dhbw.inputprocessing.postvalidate.ParameterValidator(
                        options.getFirstLetterMap().get("parameter"))));
    }

    /**
     * Customizes the mediator and registry the processor uses.
     * The default validators will still be used.
     *
     * @param mediator The mediator to use
     * @param registry The registry to use
     * @param options  The validationoptions to use for the default validators
     */
    public XMIModelProcessor(final IRestructurerMediator mediator,
                             final ITransformerRegistry registry,
                             final ValidationOptions options) {

        this.mediator = mediator;
        this.registry = registry;
        initDefaultValidators(options);
    }

    @Override
    public void initialize(final Language language) {

        postValidators.clear();
        configurePostValidators(language.getValidationOptions());

    }

    @Override
    public void addPreValidator(final IPreValidator preValidator) {

        preValidators.add(preValidator);
    }

    @Override
    public void removePreValidator(final IPreValidator preValidator) {

        preValidators.remove(preValidator);
    }

    @Override
    public void addPostValidator(final IPostValidator postValidator) {

        postValidators.add(postValidator);
    }

    @Override
    public void removePostValidator(final IPostValidator postValidator) {

        postValidators.remove(postValidator);
    }

    @Override
    public void parseModel(final @NonNull Path modelLocation)
            throws ModelParseException, ModelValidationException {

        Model model;
        try {
            Path tempFileLocation = preprocessModel(modelLocation);
            log.info("Attempting to parse model at [{}]", modelLocation);
            model = SDMetricsUtil
                    .parseXMI(tempFileLocation.toAbsolutePath().toString());
        } catch (Exception e) {
            log.error("Could not parse model due to [{}] with cause [{}]",
                      e.getClass().getSimpleName(), e.getMessage());
            throw new ModelParseException(e);
        }
        log.info("Parsing successful, clearing caches of mediator and this "
                 + "class");
        parsedClasses.clear();
        parsedInterfaces.clear();
        parsedPackages.clear();
        mediator.readyForNextRestructuring();

        log.info("Entering PreValidation phase");
        List<XMIElement> commonElements = mediator.restructure(model);
        List<String> errorMessages = new ArrayList<>();
        boolean hasErrors = false;
        for (XMIElement element : commonElements) {
            for (IPreValidator preValidator : preValidators) {
                if (preValidator.canValidate(element)) {
                    Pair<Boolean, String> result =
                            preValidator.validate(element);
                    if (!result.first()) {
                        errorMessages.add(result.second());
                        hasErrors = true;
                    }
                }
            }
        }
        log.info("Completed PreValidation phase");
        if (hasErrors) {
            for (String message : errorMessages) {
                log.error("PreValidation failed: [{}]", message);
            }
            throw new ModelValidationException(
                    "PreValidation of the Model failed. See the log"
                    + " for more details");
        }

        registry.readyForNextTransforming();
        ITransformer<UMLClass, OOType> classTransformer =
                registry.getTransformer(UMLClass.class);
        ITransformer<UMLPackage, OOPackage> packageTransformer =
                registry.getTransformer(UMLPackage.class);
        ITransformer<Interface, OOType> interfaceTransformer =
                registry.getTransformer(Interface.class);
        ITransformer<Enumeration, OOType> enumerationTransformer =
                registry.getTransformer(Enumeration.class);
        ITransformer<AppliedStereotype, TransformedStereotype>
                stereotypeITransformer =
                registry.getTransformer(AppliedStereotype.class);
        ITransformer<ElementImport, OOBaseStringWrapper> importITransformer =
                registry.getTransformer(ElementImport.class);
        ITransformer<StateMachine, StateMachineTransformer.ListOOTypeWrapper>
                stateMachineITransformer =
                registry.getTransformer(StateMachine.class);
        for (TransformedStereotype stereotype : stereotypeITransformer
                .transform(commonElements)) {
            for (OOBase target : stereotype.getTargets()) {
                target.getAppliedStereotypes().add(stereotype);
            }
        }
        parsedClasses.addAll(classTransformer.transform(commonElements));
        parsedPackages.addAll(packageTransformer.transform(commonElements));
        parsedInterfaces.addAll(interfaceTransformer.transform(commonElements));
        parsedEnums.addAll(enumerationTransformer.transform(commonElements));
        for (StateMachineTransformer.ListOOTypeWrapper wrapper :
                stateMachineITransformer
                .transform(commonElements)) {
            parsedClasses.addAll(wrapper.getWrapped());
        }
        importITransformer.transform(commonElements);

        log.info("Transformation of parsed classes successful.");

        List<OOBase> collected = new ArrayList<>(extractParsed(parsedClasses));
        collected.addAll(extractParsed(parsedEnums));
        collected.addAll(extractParsed(parsedInterfaces));
        collected.addAll(parsedPackages);

        log.info("Entering PostValidation phase");
        for (OOBase ooBase : collected) {
            for (IPostValidator postValidator : postValidators) {
                if (postValidator.canValidate(ooBase)) {
                    Pair<Boolean, String> pair = postValidator.validate(ooBase);
                    if (!pair.first()) {
                        hasErrors = true;
                        errorMessages.add(pair.second());
                    }
                }
            }
        }
        if (hasErrors) {
            for (String message : errorMessages) {
                log.error("PostValidation failed: [{}]", message);
            }
            throw new ModelValidationException(
                    "PostValidation of the Model failed. See the log"
                    + " for more details");
        }
        log.info("Completed PostValidation phase");
    }

    /**
     * Preprocesses a xmi file.
     * Preprocessing in this case means replacing an attribute {@code
     * "xmitype"} with {@code "xmi:type}. That way stereotypes will be picked
     * up when parsing the model.
     *
     * @param modelLocation The path where the model is located
     * @return Path to the preprocessed model
     *
     * @throws CodeGenerationException If an {@link IOException} occurs
     */
    private Path preprocessModel(final Path modelLocation)
            throws CodeGenerationException {

        File tempFile;
        try {
            tempFile = File.createTempFile("temp", ".xmi", new File("."));
            tempFile.deleteOnExit();
            try (Scanner scanner = new Scanner(new BufferedInputStream(
                    new FileInputStream(modelLocation.toFile())));
                 Writer writer = new BufferedWriter(new FileWriter(tempFile))) {
                String line;
                while (scanner.hasNext()) {
                    line = scanner.nextLine();
                    writer.append(line.replace("xmitype=\"", "xmi:type=\""))
                            .append("\n");
                }
            }

        } catch (IOException e) {
            throw new CodeGenerationException(e);
        }
        return tempFile.toPath();
    }

    private List<OOBase> extractParsed(final List<OOType> parsed) {

        List<OOBase> collected = new ArrayList<>(parsed);
        parsed.forEach(e -> {
            collected.addAll(e.getFields());
            collected.addAll(e.getMethods());
        });
        return collected;
    }

    /**
     * {@inheritDoc}
     *
     * @return An unmodifiable view of {@link #parsedClasses}. Invoking
     * {@link #parseModel(Path)} again will not change the contents of
     * already returned lists.
     */
    @Override
    public @NonNull List<OOType> getParsedClasses() {

        return List.copyOf(parsedClasses);
    }

    /**
     * {@inheritDoc}
     *
     * @return An unmodifiable view of {@link #parsedPackages}. Invoking
     * {@link #parseModel(Path)} again will not change the contents of
     * already returned lists.
     */
    @Override
    public @NonNull List<OOPackage> getParsedPackages() {

        return List.copyOf(parsedPackages);
    }

    /**
     * {@inheritDoc}
     *
     * @return An unmodifiable view of {@link #parsedInterfaces}. Invoking
     * {@link #parseModel(Path)} again will not change the contents of
     * already returned lists.
     */
    @Override
    public @NonNull List<OOType> getParsedInterfaces() {

        return List.copyOf(parsedInterfaces);
    }

    /**
     * {@inheritDoc}
     *
     * @return An unmodifiable view of {@link #parsedEnums}. Invoking
     * {@link #parseModel(Path)} again will not change the contents of
     * already returned lists.
     */
    @Override
    public @NonNull List<OOType> getParsedEnums() {

        return List.copyOf(parsedEnums);
    }
}
