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

import edu.horb.dhbw.datacore.model.OOField;
import edu.horb.dhbw.datacore.model.OOMethod;
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.commonstructure.Comment;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.InterfaceRealization;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public final class OOClassTransformer
        extends CachingTransformer<UMLClass, OOType> {

    /**
     * @param registry The registry to use.
     */
    public OOClassTransformer(final TransformerRegistry registry) {

        super(registry);
    }

    @Override
    public @NonNull List<OOType> transform(final @NonNull List<?> elements) {

        List<UMLClass> classes = new ArrayList<>();
        for (Object e : elements) {
            if (e instanceof UMLClass) {
                classes.add((UMLClass) e);
            }
        }
        return classes.stream().map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public OOType transform(@NonNull final UMLClass element) {

        String id = element.getId();
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        log.info("Beginning transformation of [{}]", id);
        OOType ooClass = new OOType(OOType.Type.CLASS);
        cache.put(id, ooClass);
        log.debug("Set id for [{}]", id);
        ooClass.setId(id);
        log.debug("Set name for [{}]", id);
        ooClass.setName(element.getName());
        log.debug("Set abstract for [{}]", id);
        ooClass.setAbstract(element.getIsAbstract());
        log.debug("Set final for [{}]", id);
        ooClass.setFinal(element.getIsFinalSpecialization());
        log.debug("Set visibility for [{}]", id);
        ooClass.setVisibility(element.getVisibility());
        log.debug("Set superTypes for [{}]", id);
        // TODO should work since a class should only extend classes
        ooClass.setSuperTypes(transform(element.getGeneralization().stream()
                                                .map(Generalization::getGeneral)
                                                .collect(Collectors.toList())));
        log.debug("Set fields for [{}]", id);
        ITransformer<Property, OOField> fieldITransformer =
                getTransformer(Property.class);
        ooClass.setFields(
                fieldITransformer.transform(element.getOwnedAttribute()));
        log.debug("Set methods for [{}]", id);
        ITransformer<Operation, OOMethod> methodITransformer =
                getTransformer(Operation.class);
        ooClass.setMethods(
                methodITransformer.transform(element.getOwnedOperation()));
        log.debug("Set implementedInterfaces for [{}]", id);
        List<InterfaceRealization> realizations =
                element.getInterfaceRealization();
        ITransformer<Interface, OOType> interfaceTransformer =
                getTransformer(Interface.class);
        ooClass.setImplementedInterfaces(interfaceTransformer.transform(
                realizations.stream().map(InterfaceRealization::getContract)
                        .collect(Collectors.toList())));
        log.debug("Set comments for [{}]", id);
        ooClass.setComments(
                element.getOwnedComment().stream().map(Comment::getBody)
                        .collect(Collectors.toList()));
        //TODO what happens to #ownedbehaviors, #connectors,
        // #nestedclassifiers, #substitution, #collaborationuses
        log.info("Ended transformation of [{}]", id);
        return ooClass;
    }
}
