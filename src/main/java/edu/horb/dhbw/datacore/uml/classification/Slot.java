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

package edu.horb.dhbw.datacore.uml.classification;

import edu.horb.dhbw.datacore.uml.commonstructure.Element;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a value for a {@link StructuralFeature} of an
 * {@link InstanceSpecification}.
 * See subclauses 9.8 and 9.9.20 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Slot extends Element {
    /**
     * The feature constraining the values that this slot can hold.
     */
    private StructuralFeature definingFeature;
    /**
     * The instanceSpecification owning the slot.
     */
    private InstanceSpecification owningInstance;
    /**
     * The value(s) held by the slot.
     */
    private List<ValueSpecification> value = new ArrayList<>();

    /**
     * Adds a new valueSpecification to {@link #value}.
     *
     * @param valueSpecification The valueSpecification to add
     */
    public void addValue(final ValueSpecification valueSpecification) {

        value.add(valueSpecification);
    }
}
