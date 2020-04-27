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
import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.commonstructure.Comment;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractOOTypeTransformer<F extends Classifier>
        extends CachingTransformer<F, OOType> {

    /**
     * @param registry The registry to use.
     */
    public AbstractOOTypeTransformer(final ITransformerRegistry registry) {

        super(registry);
    }

    @Override
    protected final OOType doTransformation(final F element) {

        OOType ooType = beginTransformation();

        String id = element.getId();
        log.info("Beginning transformation of [{}]", id);
        cache.put(id, ooType);
        log.debug("Set id for [{}]", id);
        ooType.setId(id);
        log.debug("Set name for [{}]", id);
        ooType.setName(element.getName());
        log.debug("Set abstract for [{}]", id);
        ooType.setAbstract(element.getIsAbstract());
        log.debug("Set final for [{}]", id);
        ooType.setFinal(element.getIsFinalSpecialization());
        log.debug("Set visibility for [{}]", id);
        ooType.setVisibility(element.getVisibility());
        log.debug("Set superTypes for [{}]", id);
        ooType.setSuperTypes(transform(element.getGeneralization().stream()
                                               .map(Generalization::getGeneral)
                                               .collect(Collectors.toList())));
        log.debug("Set container for [{}]", id);
        /*UMLPackage pkg = element.getAPackage();
        if (pkg == null) {
            ooType.setContainer(null);
        } else {
            ITransformer<UMLPackage, OOPackage> packageITransformer =
                    getTransformer(UMLPackage.class);
            ooType.setContainer(
                    packageITransformer.transform(element.getAPackage()));
        }*/
        log.debug("Set comments for [{}]", id);
        ooType.setComments(
                element.getOwnedComment().stream().map(Comment::getBody)
                        .collect(Collectors.toList()));
        final OOType returnedType = doSpecificTransformation(ooType, element);
        returnedType.getMethods()
                .forEach(ooMethod -> ooMethod.setParent(returnedType));
        returnedType.getFields()
                .forEach(ooField -> ooField.setParent(returnedType));
        return returnedType;
    }

    /**
     * This method is called before the shared attributes are transformed.
     *
     * @return An {@link OOType} with the accordingly set {@link OOType#type}
     * field.
     */
    protected abstract OOType beginTransformation();

    /**
     * @param type    The {@link OOType} where all fields shared between the
     *                differnet {@link OOType.Type}s have been set.
     * @param element The element to transform
     * @return The same type but with the specific fields set
     */
    protected abstract OOType doSpecificTransformation(OOType type, F element);
}
