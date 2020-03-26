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
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImport;
import edu.horb.dhbw.datacore.uml.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageImport;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.commonstructure.TypeImpl;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.CollaborationUse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a way to classify a set of instances according to their features.
 * See subclauses 9.2 and 9.9.4 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ClassifierImpl extends TypeImpl implements Classifier {
    /**
     * If this is {@code true} the classifier cannot be instantiated directly.
     */
    private Boolean isAbstract = Boolean.FALSE;
    /**
     * If this is {@code true} the classifier cannot be specialized.
     */
    private Boolean isFinalSpecialization = Boolean.FALSE;
    /**
     * All properties that are direct attibutes of the classifier. This
     * attribute can be derived.
     */
    private List<Property> attribute = new ArrayList<>();
    /**
     * The collaborationUses this classifier owns.
     */
    private List<CollaborationUse> collaborationUse = new ArrayList<>();
    /**
     * Features directly defined in the classifier. Does not include
     * inherited ones.
     */
    private List<Feature> feature = new ArrayList<>();

    /**
     * @return The immediate classifiers this classifier specializes
     */
    public final List<Classifier> getGeneral() {

        return generalization.stream().map(Generalization::getGeneral)
                .collect(Collectors.toList());
    }

    /**
     * The generalizations of the classifier.
     */
    private List<Generalization> generalization = new ArrayList<>();
    /**
     * All the members inherited by generalizing other classifiers. This
     * attribute can be derived.
     */
    private List<NamedElement> inheritedMember = new ArrayList<>();
    /**
     * The generalizationSet this classifier belongs to.
     */
    private List<GeneralizationSet> powertypeExtent = new ArrayList<>();
    /**
     * Indicates the collaboration representing this classifier.
     */
    private CollaborationUse representation;
    /**
     * The substitutions owned by the classifier. For UML substitution does
     * not always apply to a Generalization/Specialization relationship.
     */
    private List<Substitution> substitution = new ArrayList<>();
    /**
     * References to the owned ElementImports.
     * Copied from {@link Namespace}.
     */
    private List<ElementImport> elementImport = new ArrayList<>();
    /**
     * The elements imported into this namespace. This attribute can be derived.
     * Copied from {@link Namespace}.
     */
    private List<PackageableElement> importedMember = new ArrayList<>();
    /**
     * All {@link NamedElement}s available to this namespace. This includes
     * the imported as well as the owned ones. This attribute can be derived.
     * Copied from {@link Namespace}.
     */
    private List<NamedElement> member = new ArrayList<>();
    /**
     * The {@link NamedElement}s this namespace owns. This attribute can be
     * derived.
     * Copied from {@link Namespace}.
     */
    private List<NamedElement> ownedMember = new ArrayList<>();
    /**
     * The {@link Constraint}s owned by this namespace.
     * Copied from {@link Namespace}.
     */
    private List<Constraint> ownedRule = new ArrayList<>();
    /**
     * The {@link PackageImport}s this namespace owns.
     * Copied from {@link Namespace}.
     */
    private List<PackageImport> packageImport = new ArrayList<>();

}
