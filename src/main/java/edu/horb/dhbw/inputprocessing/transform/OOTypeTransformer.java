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

import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Enumeration;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.PrimitiveType;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public final class OOTypeTransformer extends AbstractTransformer<Type, OOType> {
    /**
     * @param registry The registry to use.
     */
    public OOTypeTransformer(final ITransformerRegistry registry) {

        super(registry);
    }

    @Override
    public @NonNull List<OOType> transform(final @NonNull List<?> elements) {

        List<Type> types = new ArrayList<>();
        for (Object element : elements) {
            if (element instanceof Type) {
                types.add((Type) element);
            }
        }
        return types.stream().map(this::transform).collect(Collectors.toList());
    }

    @Override
    public OOType transform(final @NonNull Type element) {

        String id = element.getId();
        log.info("Deciding on correct type for [{}]", id);
        if (element instanceof Interface) {
            log.info("Transforming [{}] to interface", id);
            return transformTo(Interface.class, (Interface) element);
        }
        if (element instanceof Enumeration) {
            log.info("Transforming [{}] to enumeration", id);
            return transformTo(Enumeration.class, (Enumeration) element);
        }
        if (element instanceof UMLClass) {
            log.info("Transforming [{}] to class", id);
            return transformTo(UMLClass.class, (UMLClass) element);
        }
        if (element instanceof PrimitiveType) {
            log.info("Transforming [{}] to primitiveType", id);
            return transformTo(PrimitiveType.class, (PrimitiveType) element);
        }
        //TODO support DataType
        return null;
    }

    private <T extends Type> OOType transformTo(final Class<T> tClass,
                                                final T type) {

        ITransformer<T, OOType> transformer = getTransformer(tClass);
        return transformer.transform(type);
    }
}
