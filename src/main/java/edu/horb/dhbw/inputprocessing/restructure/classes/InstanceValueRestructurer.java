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
import edu.horb.dhbw.datacore.uml.classification.InstanceSpecification;
import edu.horb.dhbw.datacore.uml.classification.InstanceValue;
import edu.horb.dhbw.inputprocessing.restructure.BaseRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class InstanceValueRestructurer
        extends BaseRestructurer<InstanceValue> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public InstanceValueRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "instancevalue");
    }

    @Override
    public InstanceValue restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of InstanceValue [{}]", id);
        log.debug("Processing instance for InstanceValue [{}]", id);
        ModelElement instance = element.getRefAttribute("instance");
        InstanceValue value = new InstanceValue();
        value.setInstance(
                delegateRestructuring(instance, InstanceSpecification.class));

        value.setId(id);
        log.info("Completed restructuring of InstanceValue [{}]", id);
        return value;
    }
}
