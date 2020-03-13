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
import edu.horb.dhbw.datacore.model.OOParameter;
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.commonstructure.Comment;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public final class OOParameterTransformer
        extends BaseTransformer<Parameter, OOParameter> {

    /**
     * @param registry The registry to use.
     */
    public OOParameterTransformer(final TransformerRegistry registry) {

        super(registry);
    }

    @Override
    public @NonNull List<OOParameter> transform(final @NonNull List<?> elements) {

        List<Parameter> classes = new ArrayList<>();
        for (Object e : elements) {
            if (e instanceof Parameter) {
                classes.add((Parameter) e);
            }
        }
        return classes.stream().map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public OOParameter transform(final @NonNull Parameter element) {

        String id = element.getId();
        log.info("Beginning transformation of [{}]", id);
        OOParameter parameter = new OOParameter();
        log.debug("Set id for [{}]", id);
        parameter.setId(id);
        log.debug("Set name for [{}]", id);
        parameter.setName(element.getName());
        log.debug("Set direction for [{}]", id);
        parameter.setDirection(element.getDirection());
        log.debug("Set cardinality for [{}]", id);
        //TODO latest place that should handle the case of just a ValueSpec
        // being present for lower/ upper limit
        parameter.setCardinality(Cardinality.getCorrectCardinality(
                element.getIsUnique(), element.getIsOrdered(),
                element.getLower(), element.getUpper()));
        log.debug("Set type for [{}]", id);
        ITransformer<Type, OOType> typeITransformer =
                getTransformer(Type.class);
        parameter.setType(typeITransformer.transform(element.getType()));
        log.debug("Set comments for [{}]", id);
        parameter.setComments(
                element.getOwnedComment().stream().map(Comment::getBody)
                        .collect(Collectors.toList()));
        return parameter;
    }
}
