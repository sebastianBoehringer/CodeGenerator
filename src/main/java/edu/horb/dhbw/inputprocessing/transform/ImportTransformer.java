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

package edu.horb.dhbw.inputprocessing.transform;

import edu.horb.dhbw.datacore.model.OOBase;
import edu.horb.dhbw.datacore.model.OOType;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.datacore.uml.commonstructure.ElementImport;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ImportTransformer
        extends AbstractTransformer<ElementImport, OOBaseStringWrapper> {
    /**
     * @param registry The registry to use.
     */
    public ImportTransformer(final ITransformerRegistry registry) {

        super(registry);
    }

    @Override
    public @NonNull List<OOBaseStringWrapper> transform(
            @NonNull final List<?> elements) {

        List<ElementImport> imports = new ArrayList<>();
        for (Object e : elements) {
            if (e instanceof ElementImport) {
                imports.add((ElementImport) e);
            }
        }
        List<OOBaseStringWrapper> result = new ArrayList<>();
        for (ElementImport elementImport : imports) {
            OOBaseStringWrapper logic = transform(elementImport);
            if (logic != null) {
                result.add(transform(elementImport));
            }
        }
        return result;
    }

    @Override
    public OOBaseStringWrapper transform(@NonNull final ElementImport element) {

        ITransformer<? extends XMIElement, OOBase> importingTransformer =
                getTransformer(LookupUtil.elementFromUMLType(
                        XMIUtil.trimNamespace(
                                element.getImportingNamespace().getUmlType()
                                        .toLowerCase())));
        List<OOBase> importer = importingTransformer.transform(
                Collections.singletonList(element.getImportingNamespace()));
        ITransformer<? extends XMIElement, OOBase> importeeTransformer =
                getTransformer(LookupUtil.elementFromUMLType(
                        XMIUtil.trimNamespace(
                                element.getImportedElement().getUmlType()
                                        .toLowerCase())));
        List<OOBase> importee = importeeTransformer.transform(
                Collections.singletonList(element.getImportedElement()));
        if (importee.isEmpty() || importer.isEmpty()) {
            return null;
        }
        if (importer instanceof OOType && importee instanceof OOType) {
            ((OOType) (importer.get(0))).getImports()
                    .add(importee.get(0).getFQName());
            return new OOBaseStringWrapper(importee.get(0).getFQName());
        }
        return null;
    }
}
