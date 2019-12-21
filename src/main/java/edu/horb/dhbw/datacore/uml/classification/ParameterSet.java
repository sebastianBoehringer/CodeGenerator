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
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Represents alternative inputs/ outputs of a {@link BehavioralFeature}. It
 * accepts only one parameter of the set as input and produces only one of
 * the parameters as output
 * <br/>
 * See subclauses 9.2 and 9.9.16 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParameterSet extends NamedElement {
    /**
     * Determines when to start/ end execution.
     */
    private List<Constraint> condition;
    /**
     * The parameters contained in this parameterSet.
     */
    private List<Parameter> parameter;

    /**
     * Adds a new parameter to {@link #parameter}.
     *
     * @param newParameter The parameter to add
     */
    public void addParameter(final Parameter newParameter) {

        parameter.add(newParameter);
    }

    /**
     * Adds a new constraint to {@link #condition}.
     *
     * @param constraint The constraint to add
     */
    public void addCondition(final Constraint constraint) {

        condition.add(constraint);
    }
}
