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
import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.classification.InstanceSpecification;
import edu.horb.dhbw.datacore.uml.classification.Slot;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import edu.horb.dhbw.util.LookupUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public final class InstanceSpecRestructurer
        extends RestructurerBase<InstanceSpecification> {
    /**
     * A map holding all the {@link InstanceSpecification}s that have already
     * been
     * processed. This maps from their xmi id to the actual instance. The map
     * is not synchronized, thus the class is most likely not threadsafe.
     */
    private static final Map<String, InstanceSpecification> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * The name of the metamodel element this restructurer can process.
     */
    private static final String PROCESSED_METAMODEL_ELEMENT =
            "instancespecification";

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public InstanceSpecRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, PROCESSED_METAMODEL_ELEMENT);
    }

    @Override
    public InstanceSpecification restructure(
            @NonNull final ModelElement element) {

        String type = element.getType().getName();
        if (!PROCESSED_METAMODEL_ELEMENT.equals(type)) {
            log.info("Trying to delegate from InstanceSpecification to "
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
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] in cache, loading instancespecification "
                             + "from cache", id);
            return ALREADY_PROCESSED.get(id);
        }
        InstanceSpecification specification = new InstanceSpecification();
        ALREADY_PROCESSED.put(id, specification);
        specification.setId(id);

        log.info("Processing name for instancespecification [{}]", id);
        String name = element.getPlainAttribute("name");
        specification.setName(name);

        log.info("Processing classifier for instancespecification [{}]", id);
        Collection<ModelElement> classifiers =
                (Collection<ModelElement>) element
                        .getSetAttribute("classifier");
        specification
                .setClassifier(delegateMany(classifiers, Classifier.class));

        log.info("Processing slots for instancespecification [{}]", id);
        Collection<ModelElement> slots =
                (Collection<ModelElement>) element.getSetAttribute("slots");
        specification.setSlot(delegateMany(slots, Slot.class));

        log.info("Processing specification for instancespecification [{}]", id);
        ModelElement spec = element.getRefAttribute("specification");
        specification.setSpecification(
                delegateRestructuring(spec, ValueSpecification.class));

        return specification;
    }

    @Override
    public void cleanCache() {

        ALREADY_PROCESSED.clear();
    }

    @Override
    public Optional<InstanceSpecification> getProcessed(
            @NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
}
