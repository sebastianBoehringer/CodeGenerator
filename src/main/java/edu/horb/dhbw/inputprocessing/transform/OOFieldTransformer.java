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

import edu.horb.dhbw.datacore.model.Cardinality;
import edu.horb.dhbw.datacore.model.OOField;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.commonstructure.Comment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public final class OOFieldTransformer
        implements ITransformer<Property, OOField> {

    private final TransformerRegistry registry;

    @Override
    public @NonNull List<OOField> transform(final @NonNull List<?> elements) {

        List<Property> classes = new ArrayList<>();
        for (Object e : elements) {
            if (e != null && Property.class.equals(e.getClass())) {
                classes.add((Property) e);
            }
        }
        return classes.stream().map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public OOField transform(@NonNull final Property element) {

        String id = element.getId();
        log.info("Beginning transformation of [{}]", id);
        OOField field = new OOField();
        log.debug("Set id for [{}]", id);
        field.setId(id);
        log.debug("Set name for [{}]", id);
        field.setName(element.getName());
        log.info("Ended transformation of [{}]", id);
        log.debug("Set visibility for [{}]", id);
        field.setVisibility(element.getVisibility());
        log.debug("Set cardinality for [{}]", field);
        //TODO latest place that should handle the case of just a ValueSpec
        // being present for lower/ upper limit
        field.setCardinality(Cardinality.getCorrectCardinality(
                element.getIsUnique(), element.getIsOrdered(),
                element.getLower(), element.getUpper()));
        //TODO use #type
        log.debug("Set type for [{}]", id);
        field.setType(null);
        log.debug("Set readOnly for [{}]", id);
        field.setReadOnly(element.getIsReadOnly());
        log.debug("Set isStatic for [{}]", id);
        field.setStatic(element.getIsStatic());
        log.debug("Set comments for [{}]", id);
        field.setComments(
                element.getOwnedComment().stream().map(Comment::getBody)
                        .collect(Collectors.toList()));
        //TODO what happens to #qualifiers, #default, #aggregation,
        // #association, #upperValue, #lowerValue
        return field;
    }
}
