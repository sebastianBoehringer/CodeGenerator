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

package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.commonstructure.Realization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the {@link Classifier}s that realize the contract laid out by a
 * {@link Component}.
 * See subclauses 11.6 and 11.8.7 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ComponentRealization extends Realization {
    /**
     * The component that is being realized by {@link #realizingClassifier}.
     */
    private Component abstraction;
    /**
     * The classifiers that are realizing {@link #abstraction}.
     */
    private List<Classifier> realizingClassifier = new ArrayList<>();
}
