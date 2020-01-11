/*
 * Copyright (c) 2019 Sebastian Boehringer.
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

package edu.horb.dhbw.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public final class GeneralizationRestructurer
        extends RestructurerBase<Generalization> {
    /**
     * A map holding all the {@link Generalization}s that have already been
     * processed. This maps from their xmi id to the actual instance. The map
     * is not synchronized, thus the class is most likely not threadsafe.
     */
    private static final Map<String, Generalization> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public GeneralizationRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "generalization");
    }

    @Override
    public Generalization restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] in cache, loading generalization from "
                             + "cache", id);
            return ALREADY_PROCESSED.get(id);
        }
        Generalization generalization = new Generalization();
        generalization.setId(id);
        ALREADY_PROCESSED.put(id, generalization);

        log.info("Processing general for enumeration [{}]", id);
        ModelElement general = element.getRefAttribute("general");
        //TODO general

        log.info("Processing specific for enumeration [{}]", id);
        ModelElement specific = element.getRefAttribute("specific");
        //TODO specific

        log.info("Processing substitutable for enumeration [{}]", id);
        String substitutable = element.getPlainAttribute("substitutable");
        generalization.setIsSubstitutable(
                StringUtils.isEmpty(substitutable) ? Boolean.TRUE : Boolean
                        .valueOf(substitutable));

        return generalization;
    }

    @Override
    public Optional<Generalization> getProcessed(final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
    @Override
    public void cleanCache() {
        ALREADY_PROCESSED.clear();
    }
}
