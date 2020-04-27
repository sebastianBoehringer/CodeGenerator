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
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClassImpl;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public final class OOClassTransformer
        extends AbstractOOTypeTransformer<UMLClass> {

    /**
     * @param registry The registry to use.
     */
    public OOClassTransformer(final ITransformerRegistry registry) {

        super(registry);
    }

    @Override
    public @NonNull List<OOType> transform(final @NonNull List<?> elements) {

        List<UMLClass> classes = new ArrayList<>();
        for (Object e : elements) {
            if (e != null && UMLClassImpl.class.equals(e.getClass())) {
                classes.add((UMLClass) e);
            }
        }
        return classes.stream().map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    protected OOType beginTransformation() {

        return new OOType(OOType.Type.CLASS);
    }

    @Override
    protected OOType doSpecificTransformation(final OOType type,
                                              final UMLClass element) {

        String id = element.getId();
        log.debug("Set fields for [{}]", id);
        ITransformer<Property, OOField> fieldITransformer =
                getTransformer(Property.class);
        type.setFields(
                fieldITransformer.transform(element.getOwnedAttribute()));
        log.debug("Set methods for [{}]", id);
        ITransformer<Operation, OOMethod> methodITransformer =
                getTransformer(Operation.class);
        type.setMethods(
                methodITransformer.transform(element.getOwnedOperation()));
        //TODO what happens to #ownedbehaviors, #connectors,
        // #nestedclassifiers, #substitution, #collaborationuses
        log.info("Ended transformation of [{}]", id);
        return type;
    }
}
