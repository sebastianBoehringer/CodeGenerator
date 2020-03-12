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

package edu.horb.dhbw.datacore.uml.simpleclassifiers;

import edu.horb.dhbw.datacore.uml.classification.ClassifierImpl;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * A classifier that can own {@link Behavior}s.
 * See subclauses 10.4 and 10.5.1 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BehavioredClassifierImpl extends ClassifierImpl
        implements BehavioredClassifier {
    /**
     * Describes the behavior of the classifier.
     */
    private Behavior classifierBehavior;
    /**
     * The {@link InterfaceRealization} relationships this classifier
     * participates in. This can be seen as a list of interfaces this
     * classifier implements
     */
    private List<InterfaceRealization> interfaceRealization = new ArrayList<>();
    /**
     * Behaviors this classifier owns.
     */
    private List<Behavior> ownedBehavior = new ArrayList<>();
}
