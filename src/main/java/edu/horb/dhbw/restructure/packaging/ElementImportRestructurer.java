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

package edu.horb.dhbw.restructure.packaging;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImport;
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public final class ElementImportRestructurer
        extends RestructurerBase<ElementImport> {
    /**
     * A map holding all the {@link ElementImport}s that have already been
     * processed. This maps from their xmi id to the actual instance. The map
     * is not synchronized, thus the class is most likely not threadsafe.
     */
    private static final Map<String, ElementImport> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ElementImportRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "elementimport");
    }

    @Override
    public ElementImport restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] for elementimport in cache", id);
            return ALREADY_PROCESSED.get(id);
        }
        ElementImport elementImport = new ElementImport();
        ALREADY_PROCESSED.put(id, elementImport);
        elementImport.setId(id);

        log.info("Processing visiblity for elementimport [{}]", id);
        String visibility = element.getPlainAttribute("visibility");
        VisibilityKind kind =
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility);
        elementImport.setVisibility(kind);

        log.info("Processing importer for elementimport [{}]", id);
        ModelElement importer = element.getRefAttribute("importer");
        elementImport.setImportingNamespace(
                delegateRestructuring(importer, Namespace.class));

        log.info("Processing imported for elementimport [{}]", id);
        ModelElement imported = element.getRefAttribute("imported");
        elementImport.setImportedElement(
                delegateRestructuring(imported, PackageableElement.class));

        return elementImport;
    }

    @Override
    public void cleanCache() {

        ALREADY_PROCESSED.clear();
    }

    @Override
    public Optional<ElementImport> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
}
