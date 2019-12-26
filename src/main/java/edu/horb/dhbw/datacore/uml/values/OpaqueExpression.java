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

package edu.horb.dhbw.datacore.uml.values;

import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.commonbehavior.Behavior;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an expression which does not use UML, i. e. it is expressed
 * using a different language.
 * See subclauses 8.3 and 8.6.16 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpaqueExpression extends ValueSpecification {
    /**
     * The textual definition of this expression.
     */
    private List<String> body = new ArrayList<>();
    /**
     * The language of a {@link #body}. These might be different ones.
     */
    private List<String> language = new ArrayList<>();
    /**
     * The behavior of this expression represented as an {@link Behavior}.
     */
    private Behavior behavior;
    /**
     * If the expression is specified with a {@link Behavior} this
     * corresponds to the behavior's return parameter. This attribute can be
     * derived.
     */
    private Parameter result;


    /**
     * Adds a new string to {@link #body}.
     *
     * @param text The string to add.
     */
    public void addBody(final String text) {

        body.add(text);
    }

    /**
     * Adds another language.
     *
     * @param lang The language to add
     */
    public void addLanguage(final String lang) {

        language.add(lang);
    }
}
