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

package edu.horb.dhbw.datacore.uml.structuredclassifiers;

import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.packages.Extension;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.BehavioredClassifier;

import java.util.List;

public interface UMLClass extends BehavioredClassifier, EncapsulatedClassifier {

    Boolean getIsActive();

    void setIsActive(Boolean isActive);

    List<Extension> getExtension();

    void setExtension(List<Extension> extension);

    List<Classifier> getNestedClassifier();

    void setNestedClassifier(List<Classifier> nestedClassifier);

    List<Operation> getOwnedOperation();

    void setOwnedOperation(List<Operation> ownedOperation);

    List<UMLClass> getSuperClass();

    void setSuperClass(List<UMLClass> superClass);
}
