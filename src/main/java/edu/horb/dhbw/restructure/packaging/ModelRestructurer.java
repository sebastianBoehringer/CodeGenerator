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

package edu.horb.dhbw.restructure.packaging;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.packages.Model;
import edu.horb.dhbw.datacore.uml.packages.ProfileApplication;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public final class ModelRestructurer extends RestructurerBase<Model> {
    /**
     * A map holding all the {@link Model}s that have already been
     * processed. This maps from their xmi id to the actual instance. The map
     * is not synchronized, thus the class is most likely not threadsafe.
     */
    private static final Map<String, Model> ALREADY_PROCESSED = new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ModelRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "model");
    }

    @Override
    public Model restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] for model in cache", id);
            return ALREADY_PROCESSED.get(id);
        }
        Model model = new Model();
        ALREADY_PROCESSED.put(id, model);
        model.setId(id);

        log.info("Processing name for model [{}]", id);
        String name = element.getPlainAttribute("name");
        model.setName(name);

        log.info("Processing ownedmember for model [{}]", id);
        Collection<ModelElement> members = (Collection<ModelElement>) element
                .getSetAttribute("ownedmembers");
        model.setOwnedMember(delegateMany(members, NamedElement.class));

        log.info("Processing profileapplications for model [{}]", id);
        Collection<ModelElement> applications =
                (Collection<ModelElement>) element
                        .getSetAttribute("profileapplications");
        model.setProfileApplication(
                delegateMany(applications, ProfileApplication.class));

        return model;
    }

    @Override
    public void cleanCache() {

        ALREADY_PROCESSED.clear();
    }

    @Override
    public Optional<Model> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
}
