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

package edu.horb.dhbw.inputprocessing.restructure.packaging;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.packages.Model;
import edu.horb.dhbw.datacore.uml.packages.UMLPackage;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public final class ModelRestructurer
        extends AbstractCachingRestructurer<Model> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ModelRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "model");
    }

    @Override
    public Model restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Model [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] for Model in cache", id);
            return processed.get(id);
        }
        Model model = new Model();
        processed.putIfAbsent(id, model);
        model.setId(id);

        log.debug("Processing name for Model [{}]", id);
        String name = element.getPlainAttribute("name");
        model.setName(name);

        log.debug("Processing umltype for Model [{}]", id);
        model.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing ownedmember for Model [{}]", id);
        Collection<ModelElement> members = (Collection<ModelElement>) element
                .getSetAttribute("ownedmembers");
        model.setOwnedMember(delegateMany(members, NamedElement.class));
        model.setPackagedElement(
                delegateMany(members, PackageableElement.class));
        log.debug("Processing ownedType for Model [{}]", id);
        model.setOwnedType(model.getPackagedElement().stream()
                                   .filter(p -> p instanceof Type)
                                   .map(p -> (Type) p)
                                   .collect(Collectors.toList()));
        for (Type type : model.getOwnedType()) {
            type.setAPackage(model);
        }
        log.debug("Processing nestedPackage for Model [{}]", id);
        model.setNestedPackage(model.getPackagedElement().stream()
                                       .filter(p -> p instanceof UMLPackage)
                                       .map(p -> (UMLPackage) p)
                                       .collect(Collectors.toList()));
        for (UMLPackage aPackage : model.getNestedPackage()) {
            aPackage.setNestingPackage(model);
        }
        log.info("Completed restructuring of Model [{}]", id);
        return model;
    }
}
