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

package edu.horb.dhbw.restructure.classes;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Generalization;
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.classification.Slot;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.PrimitiveType;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class PrimitiveRestructurer extends RestructurerBase<PrimitiveType> {

    /**
     * A map holding all the {@link PrimitiveType}s that have already been
     * processed. This maps from their xmi id to the actual instance.
     * The map is not synchronized, thus the class is most likely not
     * threadsafe.
     */
    private static final Map<String, PrimitiveType> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public PrimitiveRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "primitivetype");
    }

    @Override
    public PrimitiveType restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found datatype [{}] in cache", id);
            return ALREADY_PROCESSED.get(id);
        }
        PrimitiveType type = new PrimitiveType();
        ALREADY_PROCESSED.put(id, type);

        log.info("Processing name for PrimitiveType [{}]", id);
        String name = element.getPlainAttribute("name");
        type.setName(name);

        log.info("Processing href for PrimitiveType [{}]", id);
        String href = element.getPlainAttribute("href");
        type.setHref(URI.create(href));
        if (!StringUtils.isEmpty(href) && StringUtils.isEmpty(name)) {
            href = href.replaceFirst(".*#", "");
            type.setName(href);
            return type;
        }

        log.info("Processing ownedoperations for PrimitiveType [{}]", id);
        Collection<ModelElement> operations = (Collection<ModelElement>) element
                .getSetAttribute("ownedoperations");
        type.setOwnedOperation(delegateMany(operations, Operation.class));

        log.info("Processing ownedattributes for PrimitiveType [{}]", id);
        Collection<ModelElement> attributes = (Collection<ModelElement>) element
                .getSetAttribute("ownedattributes");
        type.setOwnedAttribute(delegateMany(attributes, Property.class));

        log.info("Processing generalizations for PrimitiveType [{}]", id);
        Collection<ModelElement> generalizations =
                (Collection<ModelElement>) element
                        .getSetAttribute("generalizations");
        type.setGeneralization(
                delegateMany(generalizations, Generalization.class));

        return type;
    }

    @Override
    public void cleanCache() {

        ALREADY_PROCESSED.clear();
    }

    @Override
    public Optional<PrimitiveType> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
}
