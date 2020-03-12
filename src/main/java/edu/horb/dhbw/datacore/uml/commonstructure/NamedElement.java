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

package edu.horb.dhbw.datacore.uml.commonstructure;

import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.values.StringExpression;

import java.util.List;

public interface NamedElement extends Element {
    String getName();

    void setName(String name);

    String getQualifiedName();

    void setQualifiedName(String qualifiedName);

    VisibilityKind getVisibility();

    void setVisibility(VisibilityKind visibility);

    List<Dependency> getClientDependency();

    void setClientDependency(List<Dependency> clientDependency);

    StringExpression getNameExpression();

    void setNameExpression(StringExpression nameExpression);

    Namespace getNamespace();

    void setNamespace(Namespace namespace);
}
