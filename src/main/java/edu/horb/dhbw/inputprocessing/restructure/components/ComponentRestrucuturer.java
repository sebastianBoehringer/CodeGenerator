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

package edu.horb.dhbw.inputprocessing.restructure.components;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.classification.Substitution;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.InterfaceRealization;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.CollaborationUse;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Component;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ComponentRealization;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Connector;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Port;
import edu.horb.dhbw.inputprocessing.restructure.CachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;

@Slf4j
public final class ComponentRestrucuturer
        extends CachingRestructurer<Component> {
    /**
     * @param mediator The mediator responsible for providing
     *                 the other {@link IRestructurer}s
     */
    public ComponentRestrucuturer(final IRestructurerMediator mediator) {

        super(mediator, "component");
    }

    @Override
    public Component restructure(final @NonNull ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Component [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading Component from cache",
                     id);
            return processed.get(id);
        }
        log.debug("Processing id for Component [{}]", id);
        Component component = new Component();
        component.setId(id);
        processed.put(id, component);

        log.debug("Processing realizations for Component [{}]", id);
        Collection<ModelElement> realizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("realizations");
        component.setRealization(
                delegateMany(realizations, ComponentRealization.class));

        log.debug("Processing members for Component [{}]", id);
        Collection<ModelElement> members =
                (Collection<ModelElement>) element.getSetAttribute("members");
        component.setMember(delegateMany(members, NamedElement.class));

        log.debug("Processing packagedElement for Component [{}]", id);
        Collection<ModelElement> packagedElements =
                (Collection<ModelElement>) element
                        .getSetAttribute("packagedElement");
        component.setPackagedElement(
                delegateMany(packagedElements, PackageableElement.class));

        log.debug("Processing name for Component [{}]", id);
        component.setName(element.getName());

        log.debug("Processing abstract for Component [{}]", id);
        component.setIsAbstract(
                Boolean.valueOf(element.getPlainAttribute("abstract")));

        log.debug("Processing leaf for Component [{}]", id);
        component.setIsFinalSpecialization(
                Boolean.valueOf(element.getPlainAttribute("leaf")));

        log.debug("Processing visibility for Component [{}]", id);
        String visibility = element.getPlainAttribute("visibility");
        component.setVisibility(
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility));

        log.debug("Processing collaborationuses for Component [{}]", id);
        Collection<ModelElement> collaborationUses =
                (Collection<ModelElement>) element
                        .getSetAttribute("collaborationuses");
        component.setCollaborationUse(
                delegateMany(collaborationUses, CollaborationUse.class));

        log.debug("Processing generalization for Component [{}]", id);
        Collection<ModelElement> generalizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("generalization");
        component.setGeneralization(
                delegateMany(generalizations, Generalization.class));

        log.debug("Processing substitution for Component [{}]", id);
        Collection<ModelElement> substitutions =
                (Collection<ModelElement>) element
                        .getSetAttribute("substitution");
        component.setSubstitution(
                delegateMany(substitutions, Substitution.class));

        log.debug("Processing ownedattributes for Component [{}]", id);
        Collection<ModelElement> attributes = (Collection<ModelElement>) element
                .getSetAttribute("ownedattributes");
        component.setOwnedAttribute(delegateMany(attributes, Property.class));

        log.debug("Processing ownedports for Component [{}]", id);
        Collection<ModelElement> ports = (Collection<ModelElement>) element
                .getSetAttribute("ownedports");
        component.setOwnedPort(delegateMany(ports, Port.class));
        component.getOwnedPort().forEach(p -> p.setOwner(component));

        log.debug("Processing ownedoperations for Component [{}]", id);
        Collection<ModelElement> operations = (Collection<ModelElement>) element
                .getSetAttribute("ownedoperations");
        component.setOwnedOperation(delegateMany(operations, Operation.class));

        log.debug("Processing nestedclassifiers for Component [{}]", id);
        Collection<ModelElement> nestedClassifiers =
                (Collection<ModelElement>) element
                        .getSetAttribute("nestedclassifiers");
        component.setNestedClassifier(
                delegateMany(nestedClassifiers, Classifier.class));

        log.debug("Processing interfacerealizations for Component [{}]", id);
        Collection<ModelElement> interfaceRealizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("interfacerealizations");
        component.setInterfaceRealization(delegateMany(interfaceRealizations,
                                                       InterfaceRealization.class));

        log.debug("Processing connectors for Component [{}]", id);
        Collection<ModelElement> connectors = (Collection<ModelElement>) element
                .getSetAttribute("connectors");
        component.setOwnedConnector(delegateMany(connectors, Connector.class));

        log.debug("Processing ownedbehaviors for Component [{}]", id);
        Collection<ModelElement> behaviors = (Collection<ModelElement>) element
                .getSetAttribute("ownedbehaviors");
        component.setOwnedBehavior(delegateMany(behaviors, Behavior.class));

        log.debug("Processing indirectlyInstantiated for Component [{}]", id);
        String indirectlyInstantiated =
                element.getPlainAttribute("indirectlyInstantiated");
        boolean value;
        if (StringUtils.isEmpty(indirectlyInstantiated)) {
            value = true;
        } else {
            value = Boolean.parseBoolean(indirectlyInstantiated);
        }
        component.setIsIndirectlyInstantiated(value);

        component.getOwnedOperation().forEach(o -> o.setOwner(component));
        component.getOwnedAttribute().forEach(a -> a.setOwner(component));
        log.info("Completed restructuring of Component [{}]", id);
        return component;
    }
}
