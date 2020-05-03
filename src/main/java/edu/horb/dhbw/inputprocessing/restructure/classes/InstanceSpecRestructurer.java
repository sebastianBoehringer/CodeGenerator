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
import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.InstanceSpecification;
import edu.horb.dhbw.datacore.uml.classification.Slot;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.inputprocessing.restructure.AbstractCachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public final class InstanceSpecRestructurer
        extends AbstractCachingRestructurer<InstanceSpecification> {
    /**
     * The name of the metamodel element this restructurer can process.
     */
    private static final String PROCESSED_METAMODEL_ELEMENT =
            "instancespecification";

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public InstanceSpecRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, PROCESSED_METAMODEL_ELEMENT);
    }

    @Override
    public InstanceSpecification restructure(
            @NonNull final ModelElement element) {

        String type = XMIUtil.getUMLType(element);
        if (!PROCESSED_METAMODEL_ELEMENT.equals(type)) {
            log.debug("Trying to delegate from InstanceSpecification to "
                              + "specialized type [{}]", type);
            Class<? extends InstanceSpecification> aClass =
                    LookupUtil.instanceSpecFromUMLType(type);
            if (aClass == null) {
                log.warn("Did not find matching class for [{}], restructuring"
                                 + " as InstanceSpecification", type);
            } else {
                return delegateRestructuring(element, aClass);
            }
        }
        String id = element.getXMIID();
        log.info("Beginning restructuring of InstanceSpecification [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading instancespecification "
                             + "from cache", id);
            return processed.get(id);
        }
        InstanceSpecification specification = new InstanceSpecification();
        processed.putIfAbsent(id, specification);
        specification.setId(id);

        log.debug("Processing umltype for InstanceSpecification [{}]", id);
        specification.setUmlType(element.getPlainAttribute("umltype"));

        log.debug("Processing name for InstanceSpecification [{}]", id);
        String name = element.getPlainAttribute("name");
        specification.setName(name);

        log.debug("Processing classifier for InstanceSpecification [{}]", id);
        Collection<ModelElement> classifiers =
                (Collection<ModelElement>) element
                        .getSetAttribute("classifier");
        specification
                .setClassifier(delegateMany(classifiers, Classifier.class));

        log.debug("Processing slots for InstanceSpecification [{}]", id);
        Collection<ModelElement> slots =
                (Collection<ModelElement>) element.getSetAttribute("slots");
        specification.setSlot(delegateMany(slots, Slot.class));

        log.debug("Processing specification for InstanceSpecification [{}]",
                  id);
        ModelElement spec = element.getRefAttribute("specification");
        specification.setSpecification(
                delegateRestructuring(spec, ValueSpecification.class));
        log.info("Completed restructuring of InstanceSpecification [{}]", id);
        return specification;
    }
}
