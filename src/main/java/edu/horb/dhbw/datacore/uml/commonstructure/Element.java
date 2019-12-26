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

import edu.horb.dhbw.datacore.uml.CommonElements;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a part of a model.
 * This is the basic building block of UML.
 * See subclauses 7.2 and 7.8.6 of the UML specification for more details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Element extends CommonElements {
    /**
     * The comments owned by this element.
     */
    private List<Comment> ownedComment = new ArrayList<>();
    /**
     * The elements owned by this element.
     */
    private List<Element> ownedElement = new ArrayList<>();
    /**
     * The owner of this element.
     */
    private Element owner;

    /**
     * Adds a new comment to the {@link #ownedComment}s.
     *
     * @param comment The comment to add
     */
    public void addComment(final Comment comment) {

        ownedComment.add(comment);
    }

    /**
     * Adds an element to {@link #ownedElement}.
     *
     * @param element The element to add
     */
    public void addOwnedElement(final Element element) {

        ownedElement.add(element);
    }
}
