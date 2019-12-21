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

import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an instance of the model(ed element). This can be used as a way
 * to represent a (default) constructor.
 * <br/>
 * See subclauses 9.8 and 9.9.9 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstanceSpecification extends PackageableElement {
    /**
     * A slot represents the value of a {@link StructuralFeature} of the
     * classifier specified by this instanceSpecification.
     */
    private List<Slot> slot = new ArrayList<>();
    /**
     * Specifies how to construct the instance.
     */
    private ValueSpecification specification;
    /**
     * The classifier(s) of the specified instance.
     */
    private List<Classifier> classifier = new ArrayList<>();

    /**
     * Adds a new classifier to {@link #classifier}.
     *
     * @param newClassifier The classifier to add
     */
    public void addClassifier(final Classifier newClassifier) {

        classifier.add(newClassifier);
    }

    /**
     * Adds a new slot to {@link #slot}.
     *
     * @param newSlot The slot to add
     */
    public void addSlot(final Slot newSlot) {

        slot.add(newSlot);
    }

}
