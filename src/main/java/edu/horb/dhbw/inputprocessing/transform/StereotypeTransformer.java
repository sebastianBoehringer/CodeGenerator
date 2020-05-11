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
import edu.horb.dhbw.datacore.model.TransformedStereotype;
import edu.horb.dhbw.datacore.uml.AppliedStereotype;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class StereotypeTransformer
        extends AbstractTransformer<AppliedStereotype, TransformedStereotype> {


    public StereotypeTransformer(final ITransformerRegistry registry) {

        super(registry);
    }

    @Override
    public @NonNull List<TransformedStereotype> transform(final @NonNull List<?> elements) {

        List<AppliedStereotype> stereotypes =
                elements.stream().filter(e -> e instanceof AppliedStereotype)
                        .map(e -> (AppliedStereotype) e)
                        .collect(Collectors.toList());
        return stereotypes.stream().map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public TransformedStereotype transform(final @NonNull AppliedStereotype element) {

        TransformedStereotype stereotype =
                new TransformedStereotype(element.getUmlType());
        stereotype.setId(element.getId());
        stereotype.getMultiPlains().putAll(element.getMultiPlains());
        stereotype.getSinglePlain().putAll(element.getSinglePlain());
        for (Map.Entry<String, List<XMIElement>> entry : element.getMultiRefs()
                .entrySet()) {
            String name = entry.getKey();
            List<XMIElement> elements = entry.getValue();
            stereotype.getMultiRefs().put(name, new ArrayList<>());
            for (XMIElement part : elements) {
                ITransformer<? extends XMIElement, OOBase> transformer =
                        getTransformer(LookupUtil.elementFromUMLType(
                                XMIUtil.trimNamespace(
                                        part.getUmlType().toLowerCase())));
                List<OOBase> processed =
                        transformer.transform(Collections.singletonList(part));
                if (!ListUtils.isEmpty(processed)) {
                    stereotype.getMultiRefs().get(name).add(processed.get(0));
                    if ("targets".equals(name)) {
                        stereotype.getTargets().add(processed.get(0));
                    }
                }
            }
        }

        return stereotype;
    }
}
