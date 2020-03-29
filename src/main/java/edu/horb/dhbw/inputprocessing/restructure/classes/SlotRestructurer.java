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
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.classification.Slot;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.inputprocessing.restructure.CachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public final class SlotRestructurer extends CachingRestructurer<Slot> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public SlotRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "slot");
    }

    @Override
    public Slot restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Slot [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading Slot from cache", id);
            return processed.get(id);
        }

        Slot slot = new Slot();
        slot.setId(id);
        processed.put(id, slot);

        log.debug("Processing defining for Slot [{}]", id);
        ModelElement defining = element.getRefAttribute("defining");
        slot.setDefiningFeature(
                delegateRestructuring(defining, Property.class));

        log.debug("Processing values for Slot [{}]", id);
        Collection<ModelElement> values =
                (Collection<ModelElement>) element.getSetAttribute("value");
        slot.setValue(delegateMany(values, ValueSpecification.class));

        log.info("Completed restructuring of Slot [{}]", id);
        return slot;
    }
}
