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

package edu.horb.dhbw.inputprocessing.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.InterfaceRealization;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.inputprocessing.restructure.CachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class InterfaceRealizationRestructurer
        extends CachingRestructurer<InterfaceRealization> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public InterfaceRealizationRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "interfacerealization");
    }


    @Override
    public InterfaceRealization restructure(
            @NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading InterfaceRealization "
                             + "from cache", id);
            return processed.get(id);
        }

        InterfaceRealization realization = new InterfaceRealization();
        realization.setId(id);
        processed.put(id, realization);

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

        return realization;
    }
}
