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
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.classification.PropertyImpl;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.enums.AggregationKind;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Association;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.inputprocessing.restructure.CachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.util.LookupUtil;
import edu.horb.dhbw.util.PrimitiveTypeUtil;
import edu.horb.dhbw.util.XMIUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;

@Slf4j
public final class PropertyRestructurer extends CachingRestructurer<Property> {

    /**
     * The name of the metamodel element this restructurer can process.
     */
    private static final String PROCESSED_METAMODEL_ELEMENT = "property";

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public PropertyRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, PROCESSED_METAMODEL_ELEMENT);
    }

    @Override
    public Property restructure(@NonNull final ModelElement element) {

        String umlType = XMIUtil.getUMLType(element);
        if (!PROCESSED_METAMODEL_ELEMENT.equals(umlType)) {
            log.info("Trying to delegate from property to specialized type for"
                             + " [{}]", umlType);

            Class<? extends Property> toRestructure =
                    LookupUtil.propertyFromUMLType(umlType);
            if (toRestructure == null) {
                log.warn("Did not find matching class for [{}], restructuring "
                                 + "as property", umlType);
            } else {
                return delegateRestructuring(element, toRestructure);
            }
        }

        String id = element.getXMIID();
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading property from cache", id);
            return processed.get(id);
        }

        Property property = new PropertyImpl();
        property.setId(id);
        processed.put(id, property);

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
        if (StringUtils.isEmpty(unique)) {
            isUnique = true;
        } else {
            isUnique = Boolean.parseBoolean(unique);
        }
        property.setIsUnique(isUnique);

        log.info("Processing lower for property [{}]", id);
        String lower = element.getPlainAttribute("lower");
        if (!StringUtils.isEmpty(lower)) {
            property.setLower(Integer.parseInt(lower));
        }

        log.info("Processing upper for property [{}]", id);
        String upper = element.getPlainAttribute("upper");
        if (!StringUtils.isEmpty(upper)) {
            property.setUpper(new UnlimitedNatural(upper));
        }

        log.info("Processing lowerValue for property [{}]", id);
        ModelElement lowerValue = element.getRefAttribute("lowerValue");
        property.setLowerValue(
                delegateRestructuring(lowerValue, ValueSpecification.class));

        log.info("Processing upperValue for property [{}]", id);
        ModelElement upperValue = element.getRefAttribute("upperValue");
        property.setUpperValue(
                delegateRestructuring(upperValue, ValueSpecification.class));

        log.info("Processing propertytype for property [{}]", id);
        ModelElement type = element.getRefAttribute("propertytype");
        if (type == null) {
            String primitive = element.getPlainAttribute("href");
            property.setType(PrimitiveTypeUtil.primitiveTypeFromURL(primitive));
        } else {
            property.setType(delegateRestructuring(type, Type.class));
        }
        log.info("Processing isreadonly for property [{}]", id);
        String readOnly = element.getPlainAttribute("isreadonly");
        property.setIsReadOnly(Boolean.valueOf(readOnly));

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

        return property;
    }
}
