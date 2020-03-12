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
import edu.horb.dhbw.datacore.model.OOPackage;
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.XMIElementImpl;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.exception.ModelParseException;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.inputprocessing.transform.ITransformer;
import edu.horb.dhbw.inputprocessing.transform.TransformerRegistry;
import edu.horb.dhbw.util.SDMetricsUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * The mediator used to restructure the parsed {@link Model}.
     */
    private final IRestructurerMediator mediator;

    private final TransformerRegistry registry = new TransformerRegistry();

    /**
     * Constructs a XMIModelProcessor with an {@link IRestructurerMediator}
     * using its default mappings.
     */
    public XMIModelProcessor() {

        mediator = new IRestructurerMediator();
    }

    /**
     * @param mappings The mappings to use for the
     *                 {@link IRestructurerMediator} used by this processor.
     */
    public XMIModelProcessor(@NonNull
                             final Map<Class<? extends XMIElement>,
            IRestructurer<? extends XMIElement>> mappings) {

        mediator = new IRestructurerMediator(mappings);

    }

    @Override
    public void parseModel(final @NonNull Path modelLocation)
            throws ModelParseException {

        Model model;
        try {
            log.info("Attempting to parse model at [{}]", modelLocation);
            model = SDMetricsUtil
                    .parseXMI(modelLocation.toAbsolutePath().toString());
        } catch (Exception e) {
            log.error("Could not parse model due to [{}] with cause [{}]",
                      e.getClass().getSimpleName(), e.getMessage());
            throw new ModelParseException(e.getCause());
        }
        log.info("Parsing successful, clearing caches of mediator and this "
                         + "class");
        parsedClasses.clear();
        parsedInterfaces.clear();
        parsedPackages.clear();
        mediator.cleanCaches();
        List<XMIElement> commonElements = mediator.restructure(model);
        //TODO prevalidation here
        ITransformer<UMLClass, OOType> classTransformer =
                registry.getTransformer(UMLClass.class);
        ITransformer<UMLPackage, OOPackage> packageTransformer =
                registry.getTransformer(UMLPackage.class);
        ITransformer<Interface, OOType> interfaceTransformer =
                registry.getTransformer(Interface.class);
        parsedClasses.addAll(classTransformer.transform(commonElements));
        parsedPackages.addAll(packageTransformer.transform(commonElements));
        parsedInterfaces.addAll(interfaceTransformer.transform(commonElements));
        log.info("Transformation of parsed classes successful.");
        //TODO post validation here

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
}
