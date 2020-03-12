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
import edu.horb.dhbw.datacore.uml.commonstructure.Comment;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public final class OOInterfaceTransformer
        extends CachingTransformer<Interface, OOType> {

    /**
     * @param registry The registry to use.
     */
    public OOInterfaceTransformer(final TransformerRegistry registry) {

        super(registry);
    }

    @Override
    public @NonNull List<OOType> transform(final @NonNull List<?> elements) {

        List<Interface> classes = new ArrayList<>();
        for (Object e : elements) {
            if (e != null && Interface.class.equals(e.getClass())) {
                classes.add((Interface) e);
            }
        }
        return classes.stream().map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public OOType transform(@NonNull final Interface element) {

        String id = element.getId();
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        OOType ooInterface = new OOType(OOType.Type.INTERFACE);
        cache.put(id, ooInterface);
        log.info("Beginning transformation of [{}]", id);
        log.debug("Set id for [{}]", id);
        ooInterface.setId(id);
        log.debug("Set name for [{}]", id);
        ooInterface.setName(element.getName());
        log.debug("Set visibility for [{}]", id);
        ooInterface.setVisibility(element.getVisibility());
        //TODO use #generalizations
        log.debug("Set superTypes for [{}]", id);
        ooInterface.setSuperTypes(Collections.emptyList());
        //TODO use #ownedattributes
        log.debug("Set fields for [{}]", id);
        ooInterface.setFields(Collections.emptyList());
        //TODO use #ownedoperations
        log.debug("Set methods for [{}]", id);
        ooInterface.setMethods(Collections.emptyList());
        log.debug("Set comments for [{}]", id);
        ooInterface.setComments(
                element.getOwnedComment().stream().map(Comment::getBody)
                        .collect(Collectors.toList()));
        //TODO what happens to #ownedbehaviors, #connectors,
        // #nestedclassifiers, #substitution, #collaborationuses
        log.info("Ended transformation of [{}]", id);
        return ooInterface;
    }
}
