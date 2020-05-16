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
import edu.horb.dhbw.datacore.uml.classification.Substitution;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImport;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageImport;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.InterfaceRealization;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.CollaborationUse;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Connector;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Port;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClassImpl;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;

@Slf4j
public final class UMLClassRestructurer
        extends AbstractCachingRestructurer<UMLClass> {


    /**
     * The name of the metamodel element this restructurer processes.
     */
    private static final String PROCESSED_METAMODEL_ELEMENT = "class";

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public UMLClassRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, PROCESSED_METAMODEL_ELEMENT);
    }

    @Override
    public UMLClass restructure(final ModelElement element) {

        String umlType = XMIUtil.getUMLType(element);
        if (!PROCESSED_METAMODEL_ELEMENT.equals(umlType)) {
            log.debug("Trying to delegate from Class to specialized type "
                              + "for [{}]", umlType);
            Class<? extends UMLClass> aClass =
                    LookupUtil.classFromUMLType(umlType);
            if (aClass == null) {
                log.warn("Did not find matching class for [{}], restructuring "
                                 + "as class", umlType);
            } else {
                return delegateRestructuring(element, aClass);
            }
        }
        String id = element.getXMIID();
        log.info("Beginning restructuring of Class [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading Class from cache", id);
            return processed.get(id);
        }
        UMLClass clazz = new UMLClassImpl();
        clazz.setId(id);
        processed.putIfAbsent(id, clazz);

        log.debug("Processing umltype for Class [{}]", id);
        clazz.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing name for Class [{}]", id);
        clazz.setName(element.getName());

        log.debug("Processing abstract for Class [{}]", id);
        clazz.setIsAbstract(
                Boolean.valueOf(element.getPlainAttribute("abstract")));

        log.debug("Processing leaf for Class [{}]", id);
        clazz.setIsFinalSpecialization(
                Boolean.valueOf(element.getPlainAttribute("leaf")));

        log.debug("Processing active for Class [{}]", id);
        clazz.setIsActive(Boolean.valueOf(element.getPlainAttribute("active")));

        log.debug("Processing classifierbehavior for Class [{}]", id);
        clazz.setClassifierBehavior(delegateRestructuring(
                element.getRefAttribute("classifierbehavior"), Behavior.class));
        if (clazz.getClassifierBehavior() != null) {
            clazz.getClassifierBehavior().setContext(clazz);
        }

        log.debug("Processing visibility for Class [{}]", id);
        String visibility = element.getPlainAttribute("visibility");
        clazz.setVisibility(
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility));

        log.debug("Processing collaborationuses for Class [{}]", id);
        Collection<ModelElement> collaborationUses =
                (Collection<ModelElement>) element
                        .getSetAttribute("collaborationuses");
        clazz.setCollaborationUse(
                delegateMany(collaborationUses, CollaborationUse.class));

        log.debug("Processing generalization for Class [{}]", id);
        Collection<ModelElement> generalizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("generalization");
        clazz.setGeneralization(
                delegateMany(generalizations, Generalization.class));

        log.debug("Processing substitution for Class [{}]", id);
        Collection<ModelElement> substitutions =
                (Collection<ModelElement>) element
                        .getSetAttribute("substitution");
        clazz.setSubstitution(delegateMany(substitutions, Substitution.class));

        log.debug("Processing ownedattributes for Class [{}]", id);
        Collection<ModelElement> attributes = (Collection<ModelElement>) element
                .getSetAttribute("ownedattributes");
        clazz.setOwnedAttribute(delegateMany(attributes, Property.class));

        log.debug("Processing ownedoperations for Class [{}]", id);
        Collection<ModelElement> operations = (Collection<ModelElement>) element
                .getSetAttribute("ownedoperations");
        clazz.setOwnedOperation(delegateMany(operations, Operation.class));

        log.debug("Processing nestedclassifiers for Class [{}]", id);
        Collection<ModelElement> nestedClassifiers =
                (Collection<ModelElement>) element
                        .getSetAttribute("nestedclassifiers");
        clazz.setNestedClassifier(
                delegateMany(nestedClassifiers, Classifier.class));

        log.debug("Processing interfacerealizations for Class [{}]", id);
        Collection<ModelElement> interfaceRealizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("interfacerealizations");
        clazz.setInterfaceRealization(delegateMany(interfaceRealizations,
                                                   InterfaceRealization.class));

        log.debug("Processing connectors for Class [{}]", id);
        Collection<ModelElement> connectors = (Collection<ModelElement>) element
                .getSetAttribute("connectors");
        clazz.setOwnedConnector(delegateMany(connectors, Connector.class));

        log.debug("Processing ownedbehaviors for Class [{}]", id);
        Collection<ModelElement> behaviors = (Collection<ModelElement>) element
                .getSetAttribute("ownedbehaviors");
        clazz.setOwnedBehavior(delegateMany(behaviors, Behavior.class));

        log.debug("Processing ownedports for Class [{}]", id);
        Collection<ModelElement> ports = (Collection<ModelElement>) element
                .getSetAttribute("ownedports");

        log.debug("Processing elementimports for Class [{}]", id);
        Collection<ModelElement> elementImports =
                (Collection<ModelElement>) element
                        .getSetAttribute("elementimports");
        clazz.setElementImport(
                delegateMany(elementImports, ElementImport.class));
        clazz.getElementImport().forEach(i -> i.setImportingNamespace(clazz));

        log.debug("Processing packageimports for Class [{}]", id);
        Collection<ModelElement> packageImports =
                (Collection<ModelElement>) element
                        .getSetAttribute("packageimports");
        clazz.setPackageImport(
                delegateMany(packageImports, PackageImport.class));

        clazz.setOwnedPort(delegateMany(ports, Port.class));
        clazz.getOwnedPort().forEach(p -> p.setOwner(clazz));
        clazz.getOwnedOperation().forEach(o -> o.setOwner(clazz));
        clazz.getOwnedAttribute().forEach(a -> a.setOwner(clazz));

        log.info("Completed restructuring of Class [{}]", id);
        return clazz;
    }
}
