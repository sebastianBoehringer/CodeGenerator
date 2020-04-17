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

package edu.horb.dhbw.inputprocessing.restructure.packaging;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImport;
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImportImpl;
import edu.horb.dhbw.datacore.uml.commonstructure.Namespace;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

@Slf4j
public final class ElementImportRestructurer
        extends AbstractCachingRestructurer<ElementImport> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ElementImportRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "elementimport");
    }

    @Override
    public ElementImport restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of ElementImport [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] for ElementImport in cache", id);
            return processed.get(id);
        }
        ElementImport elementImport = new ElementImportImpl();
        processed.put(id, elementImport);
        elementImport.setId(id);

        log.debug("Processing visiblity for ElementImport [{}]", id);
        String visibility = element.getPlainAttribute("visibility");
        VisibilityKind kind =
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility);
        elementImport.setVisibility(kind);

        log.debug("Processing importer for ElementImport [{}]", id);
        ModelElement importer = element.getRefAttribute("importer");
        elementImport.setImportingNamespace(
                delegateRestructuring(importer, Namespace.class));

        log.debug("Processing imported for ElementImport [{}]", id);
        ModelElement imported = element.getRefAttribute("imported");
        elementImport.setImportedElement(
                delegateRestructuring(imported, PackageableElement.class));
        log.info("Completed restructuring of ElementImport [{}]", id);
        return elementImport;
    }
}
