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

package edu.horb.dhbw.restructure;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.values.Interval;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class IntervalRestructurer extends RestructurerBase<Interval> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public IntervalRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "interval");
    }

    @Override
    public Interval restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        Interval interval = new Interval();
        interval.setId(id);
        try {
            log.info("Processing max for interval [{}] as modelelement", id);
            ModelElement max = element.getRefAttribute("max");
            interval.setMax(
                    delegateRestructuring(max, ValueSpecification.class));
        } catch (ClassCastException e) {
            log.warn("Processing max for interval [{}] as modelelement failed",
                     id);
        }
        try {
            log.info("Processing min for interval [{}] as modelelement", id);
            ModelElement min = element.getRefAttribute("min");
            interval.setMin(
                    delegateRestructuring(min, ValueSpecification.class));
        } catch (ClassCastException e) {
            log.warn("Processing min for interval [{}] as modelelement failed",
                     id);
        }
        return interval;
    }
}
