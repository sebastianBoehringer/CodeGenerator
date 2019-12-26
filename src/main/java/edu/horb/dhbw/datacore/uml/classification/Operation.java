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

import edu.horb.dhbw.datacore.uml.commonstructure.Constraint;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.DataType;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.Interface;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Specifies the invokation of a
 * {@link edu.horb.dhbw.datacore.uml.commonbehavior.Behavior} associated to a
 * {@link BehavioralFeature}.
 * See subclauses 9.6 and 9.9.11 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation extends BehavioralFeature {
    /**
     * If this is {@code true} the return parameter of the operation is
     * ordered. This attribute can be derived.
     */
    private Boolean isOrdered;
    /**
     * If this is {@code true} the invocation of the behavior does not change
     * the state of the system.
     */
    private Boolean isQuery = Boolean.FALSE;
    /**
     * If this is {@code true} the return parameter is unique. This attribute
     * can be derived.
     */
    private Boolean isUnique;
    /**
     * The lower multiplicity of the return parameter. This can be derived.
     */
    private Integer lower;
    /**
     * The upper multiplicity of the return parameter. This can be derived.
     */
    private UnlimitedNatural upper;
    /**
     * Specifies a constraint on the result of the invocation of the behavior.
     */
    private Constraint bodyCondition;
    /**
     * Constraints on the state of the system after the operation has completed.
     */
    private List<Constraint> postcondition = new ArrayList<>();
    /**
     * Constraints on the state of the system prior to the execution of the
     * operation.
     */
    private List<Constraint> precondition = new ArrayList<>();
    /**
     * The return type of the operation. This can be derived.
     */
    private Type type;

    /**
     * The interface that owns this operation. Either this, {@link #datatype}
     * or {@link #umlClass} is set.
     */
    private Interface anInterface;
    /**
     * The class that owns this operation. Either this, {@link #anInterface} or
     * {@link #datatype} is set.
     */
    private UMLClass umlClass;
    /**
     * The datatype that owns this operation. Either this,
     * {@link #anInterface} or {@link #umlClass} is set.
     */
    private DataType datatype;


}
