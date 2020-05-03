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

import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.AppliedStereotype;
import edu.horb.dhbw.datacore.uml.XMIElement;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public final class AppliedStereotypeRestructurer
        extends AbstractRestructurer<AppliedStereotype> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public AppliedStereotypeRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "irrelevant");
    }

    @Override
    public @NonNull List<AppliedStereotype> restructure(final Model model) {

        List<ModelElement> restructurable = new ArrayList<>();
        for (ModelElement modelElement : model) {
            if (canRestructure(modelElement)) {
                restructurable.add(modelElement);
            }
        }
        return restructurable.stream().map(this::restructure)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     *
     * @param element The element to check
     * @return {@code true} for every element that is not in the uml namespace
     */
    @Override
    public boolean canRestructure(@NonNull final ModelElement element) {

        String type = element.getPlainAttribute("umltype");
        return !type.toLowerCase().startsWith("uml:") && !StringUtils
                .isEmpty(type);
    }

    @Override
    public AppliedStereotype restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.debug("Beginning restructuring of Stereotype [{}]", id);
        AppliedStereotype stereotype = new AppliedStereotype();
        stereotype.setId(id);

        log.debug("Processing targets of Stereotype [{}]", id);
        Collection<ModelElement> targets =
                (Collection<ModelElement>) element.getSetAttribute("targets");
        stereotype.getTargets().addAll(delegateMany(targets, XMIElement.class));

        for (XMIElement target : stereotype.getTargets()) {
            target.applyStereotype(stereotype);
        }

        MetaModelElement metaModelElement = element.getType();
        Collection<String> singlePlain = new HashSet<>();
        Collection<String> singleRef = new HashSet<>();
        Collection<String> multiPlain = new HashSet<>();
        Collection<String> multiRef = new HashSet<>();
        while (metaModelElement != null) {
            for (String attributeName : metaModelElement.getAttributeNames()) {
                if (metaModelElement.isRefAttribute(attributeName)) {
                    if (metaModelElement.isSetAttribute(attributeName)) {
                        multiRef.add(attributeName);
                    } else {
                        singleRef.add(attributeName);
                    }
                } else {
                    if (metaModelElement.isSetAttribute(attributeName)) {
                        multiPlain.add(attributeName);
                    } else {
                        singlePlain.add(attributeName);
                    }
                }
            }
            metaModelElement = metaModelElement.getParent();
        }
        processSinglePlains(singlePlain, element, stereotype);
        processMultiPlains(multiPlain, element, stereotype);
        processSingleRef(singleRef, element, stereotype);
        processMultiRef(multiRef, element, stereotype);

        log.info("Finished restructuring of Stereotype [{}]", id);
        return stereotype;
    }

    private void processSinglePlains(final Collection<String> attribs,
                                     final ModelElement element,
                                     final AppliedStereotype stereotype) {

        for (String attrib : attribs) {
            log.debug("Processing [{}] for Stereotype [{}]", attrib,
                      element.getXMIID());
            stereotype.getSinglePlain()
                    .put(attrib, element.getPlainAttribute(attrib));
        }

    }

    private void processMultiPlains(final Collection<String> attribs,
                                    final ModelElement element,
                                    final AppliedStereotype stereotype) {

        for (String attrib : attribs) {
            log.debug("Processing [{}] for Stereotype [{}]", attrib,
                      element.getXMIID());
            stereotype.getMultiPlains().put(attrib, (Collection<String>) element
                    .getSetAttribute(attrib));
        }

    }

    private void processSingleRef(final Collection<String> attribs,
                                  final ModelElement element,
                                  final AppliedStereotype stereotype) {

        for (String attrib : attribs) {
            log.debug("Processing [{}] for Stereotype [{}]", attrib,
                      element.getXMIID());
            ModelElement refAttribute = element.getRefAttribute(attrib);
            if (refAttribute == null) {
                continue;
            }
            stereotype.getSingleRef().
                    put(attrib, delegateRestructuring(refAttribute, LookupUtil
                            .elementFromUMLType(
                                    XMIUtil.getUMLType(refAttribute))));
        }

    }

    private void processMultiRef(final Collection<String> attribs,
                                 final ModelElement element,
                                 final AppliedStereotype stereotype) {

        for (String attrib : attribs) {
            log.debug("Processing [{}] for Stereotype [{}]", attrib,
                      element.getXMIID());
            Collection<ModelElement> elements =
                    (Collection<ModelElement>) element.getSetAttribute(attrib);
            if (elements.isEmpty()) {
                continue;
            }
            List<XMIElement> processed = new ArrayList<>();
            for (ModelElement modelElement : elements) {
                processed.add(delegateRestructuring(modelElement, LookupUtil
                        .elementFromUMLType(XMIUtil.getUMLType(modelElement))));
            }
            processed.removeIf(Objects::isNull);
            stereotype.getMultiRefs().put(attrib, processed);
        }
    }
}
