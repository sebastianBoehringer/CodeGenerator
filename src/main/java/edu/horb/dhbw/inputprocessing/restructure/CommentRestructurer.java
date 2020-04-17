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

package edu.horb.dhbw.inputprocessing.restructure;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonstructure.Comment;
import edu.horb.dhbw.datacore.uml.commonstructure.CommentImpl;
import edu.horb.dhbw.datacore.uml.commonstructure.Element;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public final class CommentRestructurer
        extends AbstractCachingRestructurer<Comment> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public CommentRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "comment");
    }

    @Override
    public Comment restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Comment [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading constraint from cache",
                     id);
            return processed.get(id);
        }
        Comment comment = new CommentImpl();
        comment.setId(id);
        processed.put(id, comment);

        log.debug("Processing body for Comment [{}]", id);
        String body = element.getPlainAttribute("body");
        comment.setBody(body);

        log.debug("Processing annotated for Comment [{}]", id);
        Collection<ModelElement> annotated =
                (Collection<ModelElement>) element.getSetAttribute("annotated");
        comment.setAnnotatedElement(delegateMany(annotated, Element.class));

        for (Element e : comment.getAnnotatedElement()) {
            if (e != null) {
                e.getOwnedComment().add(comment);
            }
        }
        log.info("Completed restructuring of Comment [{}]", id);
        return comment;
    }
}
