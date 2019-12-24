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

package edu.horb.dhbw.datacore.uml.commonbehavior;

import edu.horb.dhbw.datacore.uml.classification.BehavioralFeature;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.classification.ParameterSet;
import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * A behavior describes how a {@link BehavioredClassifier} acts over time.
 * <br/>
 * See subclauses 13.2 and 13.4.2 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Behavior extends UMLClass {
    /**
     * If this is {@code true} the behavior can be invoked again while
     * its still executing from another invocation.
     */
    private Boolean isReentrant = Boolean.TRUE;
    /**
     * The possible arguments for the invocation of this behavior.
     */
    private List<Parameter> ownedParameter = new ArrayList<>();
    /**
     * The parameterSets this behavior owns.
     */
    private List<ParameterSet> ownedParameterSet = new ArrayList<>();
    /**
     * Conditions that must hold when the execution of the behavior finishes.
     */
    private List<Constraint> postcondition = new ArrayList<>();
    /**
     * Conditions that must be fulfilled before the behavior can be executed.
     */
    private List<Constraint> precondition = new ArrayList<>();
    /**
     * The feature this behavior implements.
     */
    private BehavioralFeature specification;
    /**
     * The context in which this behavior is defined. This attribute can be
     * derived.
     */
    private BehavioredClassifier context;

    /**
     * Adds a new parameterSet to {@link #ownedParameterSet}.
     *
     * @param set The parameterSet to add
     */
    public void addOwnedParamterSet(final ParameterSet set) {

        ownedParameterSet.add(set);
    }

    /**
     * Adds a new parameter to {@link #ownedParameter}.
     *
     * @param parameter The parameter to add.
     */
    public void addOwnedParameter(final Parameter parameter) {

        ownedParameter.add(parameter);
    }

    /**
     * Adds a new constraint to {@link #postcondition}.
     *
     * @param constraint The constraint to add.
     */
    public void addPostcondition(final Constraint constraint) {

        postcondition.add(constraint);
    }

    /**
     * Adds a new constraint to {@link #precondition}.
     *
     * @param constraint The constraint to add
     */
    public void addPrecondition(final Constraint constraint) {

        precondition.add(constraint);
    }
}
