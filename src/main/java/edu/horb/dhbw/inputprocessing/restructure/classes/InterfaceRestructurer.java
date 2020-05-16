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

package edu.horb.dhbw.inputprocessing.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImport;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageImport;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.List;

@Slf4j
public final class InterfaceRestructurer
        extends AbstractCachingRestructurer<Interface> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public InterfaceRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "interface");
    }

    @Override
    public Interface restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Interface [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading Interface from cache",
                     id);
            return processed.get(id);
        }
        Interface anInterface = new Interface();
        anInterface.setId(id);
        processed.putIfAbsent(id, anInterface);


        log.debug("Processing umltype for Interface [{}]", id);
        anInterface.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing name for Interface [{}]", id);
        String name = element.getPlainAttribute("name");
        anInterface.setName(name);

        log.debug("Processing visibility for Interface [{}]", id);
        String visibility = element.getPlainAttribute("visibility");
        anInterface.setVisibility(
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility));

        log.debug("Processing ownedattributes for Interface [{}]", id);
        Collection<ModelElement> attributes = (Collection<ModelElement>) element
                .getSetAttribute("ownedattributes");
        anInterface.setOwnedAttribute(delegateMany(attributes, Property.class));

        log.debug("Processing ownedoperations for Interface [{}]", id);
        Collection<ModelElement> operations = (Collection<ModelElement>) element
                .getSetAttribute("ownedoperations");
        anInterface
                .setOwnedOperation(delegateMany(operations, Operation.class));

        log.debug("Processing nestedclassifiers for Interface [{}]", id);
        Collection<ModelElement> nested = (Collection<ModelElement>) element
                .getSetAttribute("nestedclassifiers");
        List<Classifier> classifiers = delegateMany(nested, Classifier.class);
        anInterface.setNestedClassifier(classifiers);

        log.debug("Processing generalizations for Interface [{}]", id);
        Collection<ModelElement> generalizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("generalizations");
        anInterface.setGeneralization(
                delegateMany(generalizations, Generalization.class));

        log.debug("Processing elementimports for Interface [{}]", id);
        Collection<ModelElement> elementImports =
                (Collection<ModelElement>) element
                        .getSetAttribute("elementimports");
        anInterface.setElementImport(
                delegateMany(elementImports, ElementImport.class));
        anInterface.getElementImport()
                .forEach(i -> i.setImportingNamespace(anInterface));

        log.debug("Processing packageimports for Interface [{}]", id);
        Collection<ModelElement> packageImports =
                (Collection<ModelElement>) element
                        .getSetAttribute("packageimports");
        anInterface.setPackageImport(
                delegateMany(packageImports, PackageImport.class));

        log.info("Completed restructuring of Interface [{}]", id);
        return anInterface;
    }
}
