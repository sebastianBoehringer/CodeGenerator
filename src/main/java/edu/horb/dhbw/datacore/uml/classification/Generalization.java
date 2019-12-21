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

import edu.horb.dhbw.datacore.uml.commonstructure.DirectedRelationship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the relationship between two classifier where the one
 * specializes the other.
 * <br/>
 * See subclauses 9.2 (especially 9.2.3.2) and 9.9.7 of the UML specification
 * for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Generalization extends DirectedRelationship {
    /**
     * If {@code true} the specific classifier can be used in place of the
     * more general.
     */
    private Boolean isSubstitutable;
    /**
     * The specific classifier in the relationship.
     */
    private Classifier specific;
    /**
     * The general classifier in the relationship.
     */
    private Classifier general;
    /**
     * The generalizationSets this generalization is a part of.
     */
    private List<GeneralizationSet> generalizationSet = new ArrayList<>();

    /**
     * Adds a new generalizationSet to {@link #generalizationSet}.
     *
     * @param set The generalizationSet to add
     */
    public void addGeneralizationSet(final GeneralizationSet set) {

        generalizationSet.add(set);
    }


}
