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

package edu.horb.dhbw.datacore.uml.packages;

import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;

import java.util.List;

public interface UMLPackage extends PackageableElement, Namespace {
    String getUri();

    void setUri(String uri);

    List<UMLPackage> getNestedPackage();

    void setNestedPackage(List<UMLPackage> nestedPackage);

    UMLPackage getNestingPackage();

    void setNestingPackage(UMLPackage nestingPackage);

    List<Stereotype> getOwnedStereotype();

    void setOwnedStereotype(List<Stereotype> ownedStereotype);

    List<Type> getOwnedType();

    void setOwnedType(List<Type> ownedType);

    List<PackageableElement> getPackagedElement();

    void setPackagedElement(List<PackageableElement> packagedElement);

    List<ProfileApplication> getProfileApplication();

    void setProfileApplication(List<ProfileApplication> profileApplication);
}
