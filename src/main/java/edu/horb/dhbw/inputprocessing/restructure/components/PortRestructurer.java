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

package edu.horb.dhbw.inputprocessing.restructure.components;

import com.sdmetrics.model.ModelElement;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.enums.AggregationKind;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Association;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Port;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.inputprocessing.restructure.AbstractRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.util.PrimitiveTypeUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;

@Slf4j
public final class PortRestructurer extends AbstractRestructurer<Port> {
    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public PortRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "port");
    }

    @Override
    public Port restructure(final @NonNull ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Port [{}]", id);
        log.debug("Processing id for Port [{}]", id);
        Port port = new Port();
        port.setId(id);

        log.debug("Processing umltype for Port [{}]", id);
        port.setUmlType(element.getPlainAttribute("umltype"));
        log.debug("Processing name for Port [{}]", id);
        String name = element.getPlainAttribute("name");
        port.setName(name);
        log.debug("Processing visibility for Port [{}]", id);
        String visibility = element.getPlainAttribute("visibility");
        port.setVisibility(
                StringUtils.isEmpty(visibility) ? VisibilityKind.PUBLIC
                                                : VisibilityKind
                        .from(visibility));

        log.debug("Processing ordered for Port [{}]", id);
        String ordered = element.getPlainAttribute("ordered");
        port.setIsOrdered(Boolean.valueOf(ordered));

        log.debug("Processing unique for Port [{}]", id);
        String unique = element.getPlainAttribute("unique");
        //Default value for isUnique is true, see uml specification subclause
        // 7.8.8
        boolean isUnique;
        if (StringUtils.isEmpty(unique)) {
            isUnique = true;
        } else {
            isUnique = Boolean.parseBoolean(unique);
        }
        port.setIsUnique(isUnique);

        log.debug("Processing lowerValue for Port [{}]", id);
        ModelElement lowerValue = element.getRefAttribute("lowerValue");
        port.setLowerValue(
                delegateRestructuring(lowerValue, ValueSpecification.class));

        log.debug("Processing upperValue for Port [{}]", id);
        ModelElement upperValue = element.getRefAttribute("upperValue");
        port.setUpperValue(
                delegateRestructuring(upperValue, ValueSpecification.class));

        log.debug("Processing porttype for Port [{}]", id);
        ModelElement type = element.getRefAttribute("porttype");
        if (type == null) {
            String primitive = element.getPlainAttribute("href");
            port.setType(PrimitiveTypeUtil.primitiveTypeFromURL(primitive));
        } else {
            port.setType(delegateRestructuring(type, Type.class));
        }
        log.debug("Processing isreadonly for Port [{}]", id);
        String readOnly = element.getPlainAttribute("isreadonly");
        port.setIsReadOnly(Boolean.valueOf(readOnly));

        log.debug("Processing association for Port [{}]", id);
        ModelElement association = element.getRefAttribute("association");
        port.setAssociation(
                delegateRestructuring(association, Association.class));

        log.debug("Processing aggregation for Port [{}]", id);
        String aggregation = element.getPlainAttribute("aggregation");
        port.setAggregation(
                StringUtils.isEmpty(aggregation) ? AggregationKind.NONE
                                                 : AggregationKind
                        .from(aggregation));

        log.debug("Processing qualifiers for Port [{}]", id);
        Collection<ModelElement> qualifiers = (Collection<ModelElement>) element
                .getSetAttribute("qualifiers");
        port.setQualifier(delegateMany(qualifiers, Property.class));

        log.debug("Processing default for Port [{}]", id);
        ModelElement defaultValue = element.getRefAttribute("default");
        port.setDefaultValue(
                delegateRestructuring(defaultValue, ValueSpecification.class));

        log.debug("Processing static for Port [{}]", id);
        String isStatic = element.getPlainAttribute("static");
        port.setIsStatic(Boolean.valueOf(isStatic));

        log.debug("Processing service for Port [{}]", id);
        String service = element.getPlainAttribute("service");
        boolean isService;
        if (StringUtils.isEmpty(service)) {
            isService = true;
        } else {
            isService = Boolean.parseBoolean(service);
        }
        port.setIsService(isService);

        log.debug("Processing conjugated for Port [{}]", id);
        String conjugated = element.getPlainAttribute("conjugated");
        port.setIsConjugated(Boolean.parseBoolean(conjugated));

        log.debug("Processing behavior for Port [{}]", id);
        String behavior = element.getPlainAttribute("behavior");
        port.setIsBehavior(Boolean.parseBoolean(behavior));
        log.info("Completed restructuring of Port [{}]", id);
        return port;
    }
}
