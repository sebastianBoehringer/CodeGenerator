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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * A special way to group {@link Generalization}s and add more semantic
 * meaning to them.
 * See subclauses 9.7 and 9.9.8 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class GeneralizationSet extends PackageableElement {
    /**
     * If this is {@code true} all possible specializations have been defined.
     * Thus an instance of the general classifier has to also be an instance
     * of at least one of the specializing classifiers. E. g. male and female
     * are specializing classifiers of dog. This is a complete
     * generalizationSet as there are no more possibilities for a dog's gender.
     */
    private boolean isCovering = false;
    /**
     * If this is {@code true} the specific classifiers do not share members.
     * Otherwise they have at least one member in common. Take the example of
     * a dog's gender again. This is also a disjoint generalizationSet as a
     * dog cannot be male and female at the same time.
     */
    private boolean isDisjoint = false;
    /**
     * The generalizations that are part of this generalizationSet.
     */
    private List<Generalization> generalization = new ArrayList<>();
    /**
     * The powertype for this generalizationSet. This means that every
     * specialization can be uniquely associated with an instance of the
     * general classifier.
     */
    private Classifier powertype;

    /**
     * Adds a new generalization to {@link #generalization}.
     *
     * @param newGeneralization The generalization to add
     */
    public void addGeneralization(final Generalization newGeneralization) {

        generalization.add(newGeneralization);
    }


}
