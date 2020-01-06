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
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.InterfaceRealization;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public final class InterfaceRealizationRestructurer
        extends RestructurerBase<InterfaceRealization> {

    /**
     * A map holding all the {@link InterfaceRealization}s that have already
     * been processed. This maps from their xmi id to the actual instance.
     * The map is not synchronized, thus the class is most likely not
     * threadsafe.
     */
    private static final Map<String, InterfaceRealization> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public InterfaceRealizationRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "interfacerealization");
    }


    @Override
    public Optional<InterfaceRealization> getProcessed(final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }

    @Override
    public InterfaceRealization restructure(
            @NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] in cache, loading InterfaceRealization "
                             + "from cache", id);
            return ALREADY_PROCESSED.get(id);
        }

        InterfaceRealization realization = new InterfaceRealization();

        realization.setId(id);

        log.info("Processing name for interfacerealization [{}]", id);
        String name = element.getPlainAttribute("name");
        realization.setName(name);

        log.info("Processing contract for interfacerealization [{}]", id);
        ModelElement contract = element.getRefAttribute("contract");
        Interface anInterface =
                delegateRestructuring(contract, Interface.class);
        realization.setContract(anInterface);

        log.info("Processing implementation for interfacerealization [{}]", id);
        ModelElement implementation = element.getRefAttribute("implementation");
        UMLClass implementer =
                delegateRestructuring(implementation, UMLClass.class);
        realization.setImplementingClassifier(implementer);

        ALREADY_PROCESSED.put(realization.getId(), realization);
        return realization;
    }
}
