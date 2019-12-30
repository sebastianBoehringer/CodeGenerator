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

package edu.horb.dhbw.datacore.uml.commonstructure;

import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.values.StringExpression;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link Element} which may hold a name.
 * See subclauses 7.4 (especially 7.4.3.2) and 7.8.9 of the UML specification
 * for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class NamedElement extends Element {
    /**
     * The unqualified name of this named element. This must be unique in its
     * {@link #namespace}.
     */
    private String name;
    /**
     * The fully qualified name of this named element. This must be unique in
     * the entire model. The attribute can be derived by chaining the name of
     * the namespace(s) this element is in and finally the name of this
     * element.
     */
    private String qualifiedName;
    /**
     * Determines the visibility of this element outside of its
     * {@link #namespace}.
     */
    private VisibilityKind visibility;
    /**
     * The dependencies where the element plays the client role.
     */
    private List<Dependency> clientDependency = new ArrayList<>();
    /**
     * The expression that determines this elements name.
     */
    private StringExpression nameExpression;
    /**
     * The namespace this element belongs to. This can be derived via
     * {@link #owner}.
     */
    private Namespace namespace;

    /**
     * Adds a new dependency to {@link #clientDependency}.
     *
     * @param dependency The dependency to add
     */
    public void addClientDependency(final Dependency dependency) {

        clientDependency.add(dependency);
    }
}
