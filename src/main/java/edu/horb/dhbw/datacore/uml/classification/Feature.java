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

import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Defines a behavioral or structural characteristic of a {@link Classifier}.
 * <br/>
 * See subclauses 9.4 and 9.9.6 of the UML specification for more details.
 * Usually this would specialize an UML metaclass called RedefinableElement.
 * Since the Codegenerator does not support redefinition it instead
 * specializes {@link NamedElement} which is the only generalization of
 * RedefinalbeElement.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Feature extends NamedElement {
    /**
     * If {@code true} this feature describes the classifier itself,
     * otherwise it describes individual instances of the classifier.
     */
    private Boolean isStatic = Boolean.FALSE;
    /**
     * The classifier that has this feature. This attribute can be derived.
     */
    private Classifier featuringClassifier;
}
