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

import edu.horb.dhbw.datacore.model.OOMethod;
import edu.horb.dhbw.datacore.model.OOParameter;
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.commonstructure.Comment;
import edu.horb.dhbw.datacore.uml.commonstructure.CommentImpl;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class OOMethodTransformer implements ITransformer<Operation, OOMethod> {

    private final TransformerRegistry registry;

    @Override
    public @NonNull List<OOMethod> transform(final @NonNull List<?> elements) {

        List<Operation> classes = new ArrayList<>();
        for (Object e : elements) {
            if (e != null && Operation.class.equals(e.getClass())) {
                classes.add((Operation) e);
            }
        }
        return classes.stream().map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public OOMethod transform(final @NonNull Operation element) {

        OOMethod ooMethod = new OOMethod();
        String id = element.getId();
        log.info("Beginning transformation of [{}]", id);
        log.debug("Set id for [{}]", id);
        ooMethod.setId(id);
        log.debug("Set name for [{}]", id);
        ooMethod.setName(element.getName());
        log.debug("Set isStatic for [{}]", id);
        ooMethod.setStatic(element.getIsStatic());
        log.debug("Set visibility for [{}]", id);
        ooMethod.setVisibility(element.getVisibility());
        log.debug("Set abstract for [{}]", id);
        ooMethod.setAbstract(element.getIsAbstract());
        log.debug("Set parameters for [{}]", id);
        ITransformer<Parameter, OOParameter> parameterITransformer =
                registry.getTransformer(Parameter.class);
        ooMethod.setParameters(
                parameterITransformer.transform(element.getOwnedParameter()));
        log.debug("Set returnParam for [{}]", id);
        //TODO set return param
        log.debug("Set exceptions for [{}]", id);
        //TODO there is no transformer responsible for Type.class (not the enum)
        ITransformer<Type, OOType> typeITransformer =
                registry.getTransformer(Type.class);
        ooMethod.setExceptions(
                typeITransformer.transform(element.getRaisedException()));
        log.debug("Set comments for [{}]", id);
        ooMethod.setComments(
                element.getOwnedComment().stream().map(Comment::getBody)
                        .collect(Collectors.toList()));
        //TODO what to do with #concurrency, #isquery?
        return ooMethod;
    }
}
