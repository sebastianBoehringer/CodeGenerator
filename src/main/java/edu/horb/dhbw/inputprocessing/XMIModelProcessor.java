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
import edu.horb.dhbw.datacore.model.OOBase;
import edu.horb.dhbw.datacore.model.OOPackage;
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.model.ValidationOptions;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Enumeration;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.exception.ModelParseException;
import edu.horb.dhbw.exception.ModelValidationException;
import edu.horb.dhbw.inputprocessing.postvalidate.EnumValidator;
import edu.horb.dhbw.inputprocessing.postvalidate.FieldValidator;
import edu.horb.dhbw.inputprocessing.postvalidate.FirstLetter;
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
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.inputprocessing.transform.ITransformer;
import edu.horb.dhbw.inputprocessing.transform.TransformerRegistry;
import edu.horb.dhbw.util.SDMetricsUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public final class XMIModelProcessor implements IModelProcessor {
    /**
     * An upper limit for super types that is big enough to never be reached
     * in "normal" modelling.
     */
    private static final int ENOUGH = 123456789;
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
    @Getter
    private final List<IPreValidator> preValidators = new ArrayList<>();
    /**
     * The {@link IPostValidator} the processor applies after transforming
     * the uml {@link edu.horb.dhbw.datacore.uml.commonstructure.Element}s.
     */
    @Getter
    private final List<IPostValidator> postValidators = new ArrayList<>();

    /**
     * The mediator used to restructure the parsed {@link Model}.
     */
    private final IRestructurerMediator mediator;

    private final TransformerRegistry registry = new TransformerRegistry();

    /**
     * Constructs a XMIModelProcessor with an {@link IRestructurerMediator}
     * using its default mappings.
     *
     * @param options The validation options to configure the
     *                {@link IPostValidator}
     */
    public XMIModelProcessor(final ValidationOptions options) {

        mediator = new IRestructurerMediator();
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
        preValidators.add(new FunctionBehaviorValidator());
        preValidators.add(new BehaviorValidator());
        preValidators.addAll(Arrays.asList(new FinalStateValidator(),
                                           new PseudoStateValidator(),
                                           new RegionValidator(),
                                           new StateMachineValidator(),
                                           new StateValidator(),
                                           new TransitionValidator()));

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
     * @param mappings The mappings to use for thetyp
     *                 {@link IRestructurerMediator} used by this processor.
     */
    public XMIModelProcessor(@NonNull
                             final Map<Class<? extends XMIElement>,
            IRestructurer<? extends XMIElement>> mappings) {

        mediator = new IRestructurerMediator(mappings);

    }

    @Override
    public void parseModel(final @NonNull Path modelLocation)
            throws ModelParseException, ModelValidationException {

        Model model;
        try {
            log.info("Attempting to parse model at [{}]", modelLocation);
            model = SDMetricsUtil
                    .parseXMI(modelLocation.toAbsolutePath().toString());
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
        mediator.cleanCaches();

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

        ITransformer<UMLClass, OOType> classTransformer =
                registry.getTransformer(UMLClass.class);
        ITransformer<UMLPackage, OOPackage> packageTransformer =
                registry.getTransformer(UMLPackage.class);
        ITransformer<Interface, OOType> interfaceTransformer =
                registry.getTransformer(Interface.class);
        ITransformer<Enumeration, OOType> enumerationTransformer =
                registry.getTransformer(Enumeration.class);
        parsedClasses.addAll(classTransformer.transform(commonElements));
        parsedPackages.addAll(packageTransformer.transform(commonElements));
        parsedInterfaces.addAll(interfaceTransformer.transform(commonElements));
        parsedEnums.addAll(enumerationTransformer.transform(commonElements));
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

    private List<OOBase> extractParsed(final List<OOType> parsed) {

        List<OOBase> collected = new ArrayList<>(parsed);
        parsed.forEach(e -> {
            collected.addAll(e.getFields());
            collected.addAll(e.getMethods());
        });
        return collected;
    }
}
