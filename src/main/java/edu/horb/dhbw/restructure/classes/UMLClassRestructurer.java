/*
 * Copyright (c) 2019 Sebastian Boehringer.
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

package edu.horb.dhbw.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.classification.Substitution;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.InterfaceRealization;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.CollaborationUse;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Connector;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public final class UMLClassRestructurer extends RestructurerBase<UMLClass> {

    /**
     * A map that holds all the classes that have already been processed.
     * Definitely a liability in its current form if somebody wanted to use
     * multiple restructurers concurrently and they tried to process the same
     * Class. The mapping is from {@link UMLClass#id} to the {@link UMLClass}
     * owning the id.
     * Could be one of the mistakes falling in the category of "premature
     * optimization is the root of all evil".
     */
    private static final Map<String, UMLClass> ALREADY_PROCESSED =
            new HashMap<>();


    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public UMLClassRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "class");
    }

    @Override
    public UMLClass restructure(final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            return ALREADY_PROCESSED.get(id);
        }
        UMLClass clazz = new UMLClass();

        clazz.setId(id);

        clazz.setName(element.getName());
        log.info("Working on UML class [{}]", clazz.getName());

        //TODO context

        clazz.setIsAbstract(
                Boolean.valueOf(element.getPlainAttribute("abstract")));

        clazz.setIsFinalSpecialization(
                Boolean.valueOf(element.getPlainAttribute("leaf")));

        String visibility = element.getPlainAttribute("visibility");
        clazz.setVisibility(
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility));

        Collection<ModelElement> collaborationUses =
                (Collection<ModelElement>) element
                        .getSetAttribute("collaborationuses");
        clazz.setCollaborationUse(
                delegateMany(collaborationUses, CollaborationUse.class));

        Collection<ModelElement> generalizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("generalization");
        clazz.setGeneralization(
                delegateMany(generalizations, Generalization.class));

        Collection<ModelElement> substitutions =
                (Collection<ModelElement>) element
                        .getSetAttribute("substitution");
        clazz.setSubstitution(delegateMany(substitutions, Substitution.class));

        Collection<ModelElement> attributes = (Collection<ModelElement>) element
                .getSetAttribute("ownedattributes");
        log.info("Attributes: [{}]", attributes);
        clazz.setOwnedAttribute(delegateMany(attributes, Property.class));

        Collection<ModelElement> operations = (Collection<ModelElement>) element
                .getSetAttribute("ownedoperations");
        clazz.setOwnedOperation(delegateMany(operations, Operation.class));

        //TODO nested classifiers

        Collection<ModelElement> interfaceRealizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("interfacerealizations");
        clazz.setInterfaceRealization(delegateMany(interfaceRealizations,
                                                   InterfaceRealization.class));

        Collection<ModelElement> connectors = (Collection<ModelElement>) element
                .getSetAttribute("connectors");
        clazz.setOwnedConnector(delegateMany(connectors, Connector.class));

        Collection<ModelElement> behaviors = (Collection<ModelElement>) element
                .getSetAttribute("ownedbehaviors");
        clazz.setOwnedBehavior(delegateMany(behaviors, Behavior.class));

        log.info("Resulting class is [{}]", clazz.toString());
        log.info("Name [{}], ID [{}]", clazz.getName(), clazz.getId());
        log.info("Abstract [{}], leaf [{}]", clazz.getIsAbstract(),
                 clazz.getIsFinalSpecialization());
        log.info("Visibility [{}]", clazz.getVisibility());
        log.info("==============================================");
        ALREADY_PROCESSED.put(clazz.getId(), clazz);
        return clazz;
    }

    @Override
    public Optional<UMLClass> getProcessed(final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
}
