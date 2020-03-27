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
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.InterfaceRealization;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.CollaborationUse;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Connector;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Port;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClassImpl;
import edu.horb.dhbw.inputprocessing.restructure.CachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;

@Slf4j
public final class UMLClassRestructurer extends CachingRestructurer<UMLClass> {


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
            log.info("Trying to delegate from class to specialized type "
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
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading class from cache", id);
            return processed.get(id);
        }
        UMLClass clazz = new UMLClassImpl();
        clazz.setId(id);
        processed.put(id, clazz);

        log.info("Processing name for class [{}]", id);
        clazz.setName(element.getName());

        log.info("Processing abstract for class [{}]", id);
        clazz.setIsAbstract(
                Boolean.valueOf(element.getPlainAttribute("abstract")));

        log.info("Processing leaf for class [{}]", id);
        clazz.setIsFinalSpecialization(
                Boolean.valueOf(element.getPlainAttribute("leaf")));

        log.info("Processing visibility for class [{}]", id);
        String visibility = element.getPlainAttribute("visibility");
        clazz.setVisibility(
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility));

        log.info("Processing collaborationuses for class [{}]", id);
        Collection<ModelElement> collaborationUses =
                (Collection<ModelElement>) element
                        .getSetAttribute("collaborationuses");
        clazz.setCollaborationUse(
                delegateMany(collaborationUses, CollaborationUse.class));

        log.info("Processing generalization for class [{}]", id);
        Collection<ModelElement> generalizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("generalization");
        clazz.setGeneralization(
                delegateMany(generalizations, Generalization.class));

        log.info("Processing substitution for class [{}]", id);
        Collection<ModelElement> substitutions =
                (Collection<ModelElement>) element
                        .getSetAttribute("substitution");
        clazz.setSubstitution(delegateMany(substitutions, Substitution.class));

        log.info("Processing ownedattributes for class [{}]", id);
        Collection<ModelElement> attributes = (Collection<ModelElement>) element
                .getSetAttribute("ownedattributes");
        clazz.setOwnedAttribute(delegateMany(attributes, Property.class));

        log.info("Processing ownedoperations for class [{}]", id);
        Collection<ModelElement> operations = (Collection<ModelElement>) element
                .getSetAttribute("ownedoperations");
        clazz.setOwnedOperation(delegateMany(operations, Operation.class));

        log.info("Processing nestedclassifiers for class [{}]", id);
        Collection<ModelElement> nestedClassifiers =
                (Collection<ModelElement>) element
                        .getSetAttribute("nestedclassifiers");
        clazz.setNestedClassifier(
                delegateMany(nestedClassifiers, Classifier.class));

        log.info("Processing interfacerealizations for class [{}]", id);
        Collection<ModelElement> interfaceRealizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("interfacerealizations");
        clazz.setInterfaceRealization(delegateMany(interfaceRealizations,
                                                   InterfaceRealization.class));

        log.info("Processing connectors for class [{}]", id);
        Collection<ModelElement> connectors = (Collection<ModelElement>) element
                .getSetAttribute("connectors");
        clazz.setOwnedConnector(delegateMany(connectors, Connector.class));

        log.info("Processing ownedbehaviors for class [{}]", id);
        Collection<ModelElement> behaviors = (Collection<ModelElement>) element
                .getSetAttribute("ownedbehaviors");
        clazz.setOwnedBehavior(delegateMany(behaviors, Behavior.class));

        log.debug("Processing ownedports for class [{}]", id);
        Collection<ModelElement> ports = (Collection<ModelElement>) element
                .getSetAttribute("ownedports");
        clazz.setOwnedPort(delegateMany(ports, Port.class));
        clazz.getOwnedPort().forEach(p -> p.setOwner(clazz));
        clazz.getOwnedOperation().forEach(o -> o.setOwner(clazz));
        clazz.getOwnedAttribute().forEach(a -> a.setOwner(clazz));
        return clazz;
    }
}
