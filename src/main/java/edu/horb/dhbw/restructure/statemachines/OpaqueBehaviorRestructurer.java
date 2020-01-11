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

package edu.horb.dhbw.restructure.statemachines;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonbehavior.OpaqueBehavior;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Slf4j
public final class OpaqueBehaviorRestructurer
        extends RestructurerBase<OpaqueBehavior> {

    public OpaqueBehaviorRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "opaquebehavior");
    }

    @Override
    public OpaqueBehavior restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        OpaqueBehavior behavior = new OpaqueBehavior();
        behavior.setId(id);

        log.info("Processing body for opaquebehavior [{}]", id);
        Collection<String> body =
                (Collection<String>) element.getSetAttribute("body");
        behavior.setBody(new ArrayList<>(body));

        log.info("Processing language of opaquebehavior [{}]", id);
        Collection<String> languages =
                (Collection<String>) element.getSetAttribute("language");
        behavior.setLanguage(new ArrayList<>(languages));
        return behavior;
    }

    /**
     * No op as this restructurer does not cache.
     */
    @Override
    public void cleanCache() {

    }

    /**
     * @param id The id of an element
     * @return {@link Optional#EMPTY} as this restructurer does not cache
     */
    @Override
    public Optional<OpaqueBehavior> getProcessed(@NonNull final String id) {

        return Optional.empty();
    }
}
