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
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.CollaborationUse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a way to classify a set of instances according to their features.
 * See subclauses 9.2 and 9.9.4 of the UML specification for more details.
 * This should be a specialization of
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.Type} and
 * {@link edu.horb.dhbw.datacore.uml.commonstructure.Namespace}. It currently
 * only inherits from {@link Type}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Classifier extends Type {
    //TODO resolve inheritance
    /**
     * If this is {@code true} the classifier cannot be instantiated directly.
     */
    private Boolean isAbstract = Boolean.FALSE;

    /**
     * The generalizing classifiers. This attribute can be derived.
     */
    private List<Classifier> general = new ArrayList<>();
    /**
     * If this is {@code true} the classifier cannot be specialized.
     */
    private Boolean isFinalSpecialization = Boolean.FALSE;
    /**
     * The collaborationUse this classifier owns.
     */
    private List<CollaborationUse> collaborationUse = new ArrayList<>();
    /**
     * Indicates the collaboration representing this classifier.
     */
    private CollaborationUse representation;
    /**
     * The generalizations of the classifier.
     */
    private List<Generalization> generalization = new ArrayList<>();
    /**
     * The substitutions owned by the classifier. For UML substitution does
     * not always apply to a Generalization/Specialization relationship.
     */
    private List<Substitution> substitution = new ArrayList<>();
    /**
     * All properties that are direct attibutes of the classifier. This
     * attribute can be derived.
     */
    private List<Property> attribute = new ArrayList<>();
    /**
     * All the members inherited by generalizing other classifiers. This
     * attribute can be derived.
     */
    private List<NamedElement> inheritedMember = new ArrayList<>();
    /**
     * Features directly defined in the classifier. Does not include
     * inherited ones.
     */
    private List<Feature> feature = new ArrayList<>();
    /**
     * The generalizationSet this classifier belongs to.
     */
    private GeneralizationSet powertypeExtent;


    /**
     * Adds a new collaborationUse to {@link #collaborationUse}.
     *
     * @param use The collaborationUse to add
     */
    public void addCollaborationUse(final CollaborationUse use) {

        collaborationUse.add(use);
    }

    /**
     * Adds a new generalization to {@link #generalization}.
     *
     * @param newGeneralization The generalization to add
     */
    public void addGeneralization(final Generalization newGeneralization) {

        generalization.add(newGeneralization);
    }

    /**
     * Adds a new substitution to {@link #substitution}.
     *
     * @param substitute The substitution to add
     */
    public void addSubstitution(final Substitution substitute) {

        substitution.add(substitute);
    }

    /**
     * Adds a new property to {@link #attribute}.
     *
     * @param property The property to add
     */
    public void addAttribute(final Property property) {

        attribute.add(property);
    }

    /**
     * Adds a new namedElement to {@link #inheritedMember}.
     *
     * @param element The namedElement to add
     */
    public void addInheritedMember(final NamedElement element) {

        inheritedMember.add(element);
    }

    /**
     * Adds a new feature to {@link #feature}.
     *
     * @param newFeature The feature to add
     */
    public void addFeature(final Feature newFeature) {

        feature.add(newFeature);
    }

    /**
     * Adds a new classifier to {@link #general}.
     *
     * @param classifier The classifier to add
     */
    public void addGeneral(final Classifier classifier) {

        general.add(classifier);
    }
}
