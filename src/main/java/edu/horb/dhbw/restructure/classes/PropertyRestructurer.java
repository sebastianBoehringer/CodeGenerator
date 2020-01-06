/*
 * Copyright (c) 2019 Sebastian Boehringer.
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
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.enums.AggregationKind;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Association;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public final class PropertyRestructurer extends RestructurerBase<Property> {

    /**
     * A map holding all the {@link Property}s that have already been
     * processed. This maps from their xmi id to the actual instance.
     * The map is not synchronized, thus the class is most likely not
     * threadsafe.
     */
    private static final Map<String, Property> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public PropertyRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "property");
    }

    @Override
    public Property restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] in cache, loading property from cache", id);
            return ALREADY_PROCESSED.get(id);
        }
        Property property = new Property();

        property.setId(id);

        //TODO context
        log.info("Processing name for property [{}]", id);
        property.setName(element.getName());

        log.info("Processing visibility for property [{}]", id);
        String visibility = element.getPlainAttribute("visibility");
        property.setVisibility(
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility));

        log.info("Processing ordered for property [{}]", id);
        String ordered = element.getPlainAttribute("ordered");
        property.setIsOrdered(Boolean.valueOf(ordered));

        log.info("Processing unique for property [{}]", id);
        String unique = element.getPlainAttribute("unique");
        //Default value for isUnique is true, see uml specification subclause
        // 7.8.8
        boolean isUnique;
        if (unique == null || "".equals(unique)) {
            isUnique = true;
        } else {
            isUnique = Boolean.parseBoolean(unique);
        }
        property.setIsUnique(isUnique);

        log.info("Processing lower for property [{}]", id);
        String lower = element.getPlainAttribute("lower");
        if (lower != null && !("".equals(lower))) {
            property.setLower(Integer.parseInt(lower));
        }

        log.info("Processing upper for property [{}]", id);
        String upper = element.getPlainAttribute("upper");
        if (upper != null && !("".equals(upper))) {
            property.setUpper(new UnlimitedNatural(upper));
        }

        log.info("Processing lowerValue for parameter [{}]", id);
        ModelElement lowerValue = element.getRefAttribute("lowerValue");
        property.setLowerValue(
                delegateRestructuring(lowerValue, ValueSpecification.class));

        log.info("Processing upperValue for parameter [{}]", id);
        ModelElement upperValue = element.getRefAttribute("upperValue");
        property.setUpperValue(
                delegateRestructuring(upperValue, ValueSpecification.class));

        log.info("Processing propertytype for property [{}]", id);
        ModelElement type = element.getRefAttribute("propertytype");
        property.setType(delegateRestructuring(type, Type.class));

        log.info("Processing isreadonly for property [{}]", id);
        String readOnly = element.getPlainAttribute("isreadonly");
        property.setIsReadOnly(Boolean.valueOf(readOnly));

        //TODO Association has subclasses
        log.info("Processing association for property [{}]", id);
        ModelElement association = element.getRefAttribute("association");
        property.setAssociation(
                delegateRestructuring(association, Association.class));

        log.info("Processing aggregation for property [{}]", id);
        String aggregation = element.getPlainAttribute("aggregation");
        property.setAggregation(
                StringUtils.isEmpty(aggregation) ? AggregationKind.NONE
                                                 : AggregationKind
                        .from(aggregation));

        //TODO Property has subclasses
        log.info("Processing qualifiers for property [{}]", id);
        Collection<ModelElement> qualifiers = (Collection<ModelElement>) element
                .getSetAttribute("qualifiers");
        property.setQualifier(delegateMany(qualifiers, Property.class));

        log.info("Processing default for property [{}]", id);
        ModelElement defaultValue = element.getRefAttribute("default");
        property.setDefaultValue(
                delegateRestructuring(defaultValue, ValueSpecification.class));

        log.info("Processing static for property [{}]", id);
        String isStatic = element.getPlainAttribute("static");
        property.setIsStatic(Boolean.valueOf(isStatic));

        ALREADY_PROCESSED.put(property.getId(), property);
        return property;
    }

    @Override
    public Optional<Property> getProcessed(final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
}
