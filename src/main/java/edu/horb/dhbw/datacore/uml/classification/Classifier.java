/*
 * Copyright (c) 2020 Sebastian Boehringer.
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
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.CollaborationUse;

import java.util.List;

public interface Classifier extends Type, Namespace {
    Boolean getIsAbstract();

    void setIsAbstract(Boolean isAbstract);

    Boolean getIsFinalSpecialization();

    void setIsFinalSpecialization(Boolean finalSpecialization);

    List<Property> getAttribute();

    void setAttribute(List<Property> attribute);

    List<CollaborationUse> getCollaborationUse();

    void setCollaborationUse(List<CollaborationUse> collaborationUse);

    List<Feature> getFeature();

    void setFeature(List<Feature> feature);

    List<Classifier> getGeneral();

    void setGeneral(List<Classifier> general);

    List<Generalization> getGeneralization();

    void setGeneralization(List<Generalization> generalization);

    List<NamedElement> getInheritedMember();

    void setInheritedMember(List<NamedElement> inheritedMember);

    List<GeneralizationSet> getPowertypeExtent();

    void setPowertypeExtent(List<GeneralizationSet> powertypeExtent);

    CollaborationUse getRepresentation();

    void setRepresentation(CollaborationUse representation);

    List<Substitution> getSubstitution();

    void setSubstitution(List<Substitution> substitution);
}
