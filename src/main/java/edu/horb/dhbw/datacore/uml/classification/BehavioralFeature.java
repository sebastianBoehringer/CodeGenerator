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

import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.enums.CallConcurrencyKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Specifies an aspect of the behavior of the owning {@link Classifier}.
 * See subclauses 9.4 and 9.9.2 of the UML specification for more details.
 * This should be a specialization of {@link Feature} and
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}. It currently
 * only inherits from {@link Feature}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BehavioralFeature extends Feature {
    //TODO resolve inheritance
    /**
     * Specifies the semantics of concurrent calls.
     */
    private CallConcurrencyKind concurrency = CallConcurrencyKind.SEQUENTIAL;
    /**
     * If this attribute is {@code true}, the behavioralFeature has no
     * implementation.
     */
    private Boolean isAbstract = Boolean.FALSE;
    /**
     * The Behavior that implements the feature.
     */
    private List<Behavior> method;
    /**
     * The parameters for this feature.
     */
    private List<Parameter> ownedParameter = new ArrayList<>();
    /**
     * The parameterSets owned by the feature.
     */
    private List<ParameterSet> ownedParameterSet = new ArrayList<>();
    /**
     * The exceptions this feature can raise when it is invoked.
     */
    private List<Type> raisedException = new ArrayList<>();

    /**
     * Adds a new parameterSet to {@link #ownedParameterSet}.
     *
     * @param parameterSet The parameterSet to add
     */
    public void addParameterSet(final ParameterSet parameterSet) {

        ownedParameterSet.add(parameterSet);
    }

    /**
     * Adds a new type of exception to {@link #raisedException}.
     *
     * @param exception The type of exception to add
     */
    public void addException(final Type exception) {

        raisedException.add(exception);
    }

    /**
     * Adds a new parameter to {@link #ownedParameter}.
     *
     * @param parameter The parameter to add
     */
    public void addParameter(final Parameter parameter) {

        ownedParameter.add(parameter);
    }

    /**
     * Adds a new behavior to {@link #method}.
     *
     * @param behavior The behavior to add
     */
    public void addMethod(final Behavior behavior) {

        method.add(behavior);
    }


}
