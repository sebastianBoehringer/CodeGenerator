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
import edu.horb.dhbw.datacore.uml.packages.Model;
import edu.horb.dhbw.inputprocessing.restructure.CachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public final class ModelRestructurer extends CachingRestructurer<Model> {

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
        if (processed.containsKey(id)) {
            log.info("Found id [{}] for model in cache", id);
            return processed.get(id);
        }
        Model model = new Model();
        processed.put(id, model);
        model.setId(id);

        log.info("Processing name for model [{}]", id);
        String name = element.getPlainAttribute("name");
        model.setName(name);

        log.info("Processing ownedmember for model [{}]", id);
        Collection<ModelElement> members = (Collection<ModelElement>) element
                .getSetAttribute("ownedmembers");
        model.setOwnedMember(delegateMany(members, NamedElement.class));

        return model;
    }
}
