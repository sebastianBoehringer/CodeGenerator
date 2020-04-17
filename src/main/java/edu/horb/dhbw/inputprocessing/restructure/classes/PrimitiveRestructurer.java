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

package edu.horb.dhbw.inputprocessing.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.PrimitiveType;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.PrimitiveTypeImpl;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.net.URI;
import java.util.Collection;

@Slf4j
public final class PrimitiveRestructurer
        extends AbstractCachingRestructurer<PrimitiveType> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public PrimitiveRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "primitivetype");
    }

    @Override
    public PrimitiveType restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of PrimitiveType [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found datatype [{}] in cache", id);
            return processed.get(id);
        }
        PrimitiveType type = new PrimitiveTypeImpl();
        processed.put(id, type);

        log.debug("Processing name for PrimitiveType [{}]", id);
        String name = element.getPlainAttribute("name");
        type.setName(name);

        log.debug("Processing href for PrimitiveType [{}]", id);
        String href = element.getPlainAttribute("href");
        type.setHref(URI.create(href));
        if (!StringUtils.isEmpty(href) && StringUtils.isEmpty(name)) {
            href = href.replaceFirst(".*#", "");
            type.setName(href);
            return type;
        }

        log.debug("Processing ownedoperations for PrimitiveType [{}]", id);
        Collection<ModelElement> operations = (Collection<ModelElement>) element
                .getSetAttribute("ownedoperations");
        type.setOwnedOperation(delegateMany(operations, Operation.class));

        log.debug("Processing ownedattributes for PrimitiveType [{}]", id);
        Collection<ModelElement> attributes = (Collection<ModelElement>) element
                .getSetAttribute("ownedattributes");
        type.setOwnedAttribute(delegateMany(attributes, Property.class));

        log.debug("Processing generalizations for PrimitiveType [{}]", id);
        Collection<ModelElement> generalizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("generalizations");
        type.setGeneralization(
                delegateMany(generalizations, Generalization.class));
        log.info("Completed restructuring of PrimitiveType [{}]", id);
        return type;
    }
}
