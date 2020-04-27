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

package edu.horb.dhbw.inputprocessing.transform;

import edu.horb.dhbw.datacore.model.OOPackage;
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Component;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ComponentRealization;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public final class OOPackageTransformer
        extends CachingTransformer<Component, OOPackage> {
    /**
     * @param registry The registry to use.
     */
    public OOPackageTransformer(final ITransformerRegistry registry) {

        super(registry);
    }

    @Override
    public OOPackage doTransformation(@NonNull final Component element) {

        String id = element.getId();
        log.info("Beginning transformation of [{}]", id);
        log.debug("Set id for [{}]", id);
        OOPackage ooPackage = new OOPackage();
        cache.put(id, ooPackage);
        ooPackage.setId(id);
        log.debug("Set name for [{}]", id);
        ooPackage.setName(element.getName());
        log.debug("Set container for [{}]", id);
        List<PackageableElement> packagedElement = element.getPackagedElement();
        ITransformer<Type, OOType> classifierOOTypeITransformer =
                getTransformer(Type.class);
        ooPackage.getContainedElements().addAll(classifierOOTypeITransformer
                                                        .transform(
                                                                packagedElement));
        List<ComponentRealization> realizations = element.getRealization();
        List<List<Classifier>> collect = realizations.stream()
                .map(ComponentRealization::getRealizingClassifier)
                .collect(Collectors.toList());
        List<Classifier> classifiers = new ArrayList<>();
        collect.forEach(classifiers::addAll);
        List<OOType> realizingTypes =
                classifierOOTypeITransformer.transform(classifiers);
        List<OOPackage> realizingComponents = this.transform(classifiers);
        realizingComponents.forEach(c -> c.setParent(ooPackage));
        realizingTypes.forEach(t -> t.setParent(ooPackage));
        ooPackage.getContainedElements().addAll(realizingTypes);

        return ooPackage;
    }

    @Override
    public @NonNull List<OOPackage> transform(final @NonNull List<?> elements) {

        List<Component> classes = new ArrayList<>();
        for (Object e : elements) {
            if (e instanceof Component) {
                classes.add((Component) e);
            }
        }
        return classes.stream().map(this::transform)
                .collect(Collectors.toList());
    }
}
