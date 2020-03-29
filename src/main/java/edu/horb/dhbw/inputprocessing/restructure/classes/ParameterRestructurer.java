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
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.enums.ParameterDirectionKind;
import edu.horb.dhbw.datacore.uml.enums.ParameterEffectKind;
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.inputprocessing.restructure.CachingRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.util.PrimitiveTypeUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

@Slf4j
public final class ParameterRestructurer
        extends CachingRestructurer<Parameter> {

    /**
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ParameterRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "parameter");
    }

    @Override
    public Parameter restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        log.info("Beginning restructuring of Parameter [{}]", id);
        if (processed.containsKey(id)) {
            log.info("Found id [{}] in cache, loading Parameter from cache",
                     id);
            return processed.get(id);
        }
        Parameter parameter = new Parameter();
        parameter.setId(id);
        processed.put(id, parameter);

        log.debug("Processing name for Parameter [{}]", id);
        String name = element.getPlainAttribute("name");
        parameter.setName(name);

        log.debug("Processing ordered for Parameter [{}]", id);
        String ordered = element.getPlainAttribute("ordered");
        parameter.setIsOrdered(Boolean.valueOf(ordered));

        log.debug("Processing unique for Parameter [{}]", id);
        String unique = element.getPlainAttribute("unique");
        boolean isUnique;
        //Default value for isUnique is true, see uml specification subclause
        // 7.8.8
        if (unique == null || "".equals(unique)) {
            isUnique = true;
        } else {
            isUnique = Boolean.parseBoolean(unique);
        }
        parameter.setIsUnique(isUnique);

        log.debug("Processing lower for Parameter [{}]", id);
        String lower = element.getPlainAttribute("lower");
        if (!StringUtils.isEmpty(lower)) {
            parameter.setLower(Integer.parseInt(lower));
        }
        log.debug("Processing upper for Parameter [{}]", id);
        String upper = element.getPlainAttribute("upper");
        if (!StringUtils.isEmpty(upper)) {
            parameter.setUpper(new UnlimitedNatural(upper));
        }

        log.debug("Processing lowerValue for Parameter [{}]", id);
        ModelElement lowerValue = element.getRefAttribute("lowerValue");
        parameter.setLowerValue(
                delegateRestructuring(lowerValue, ValueSpecification.class));

        log.debug("Processing upperValue for Parameter [{}]", id);
        ModelElement upperValue = element.getRefAttribute("upperValue");
        parameter.setUpperValue(
                delegateRestructuring(upperValue, ValueSpecification.class));

        log.debug("Processing kind for Parameter [{}]", id);
        String kind = element.getPlainAttribute("kind");
        ParameterDirectionKind directionKind =
                StringUtils.isEmpty(kind) ? ParameterDirectionKind.IN
                                          : ParameterDirectionKind.from(kind);
        parameter.setDirection(directionKind);

        log.debug("Processing parametertype for Parameter [{}]", id);
        ModelElement type = element.getRefAttribute("parametertype");
        if (type == null) {
            String primitive = element.getPlainAttribute("href");
            parameter
                    .setType(PrimitiveTypeUtil.primitiveTypeFromURL(primitive));
        } else {
            parameter.setType(delegateRestructuring(type, Type.class));
        }

        log.debug("Processing effect for Parameter [{}]", id);
        String effect = element.getPlainAttribute("effect");
        ParameterEffectKind effectKind =
                StringUtils.isEmpty(effect) ? ParameterEffectKind.UNDEFINED
                                            : ParameterEffectKind.from(effect);
        parameter.setEffect(effectKind);

        log.debug("Processing exception for Parameter [{}]", id);
        String exception = element.getPlainAttribute("exception");
        parameter.setIsException(Boolean.valueOf(exception));

        log.debug("Processing stream for Parameter [{}]", id);
        String stream = element.getPlainAttribute("stream");
        parameter.setIsStream(Boolean.valueOf(stream));

        log.debug("Processing default for Parameter [{}]", id);
        ModelElement defaults = element.getRefAttribute("default");
        parameter.setDefaultValue(
                delegateRestructuring(defaults, ValueSpecification.class));

        log.debug("Processing defaultstring for Parameter [{}]", id);
        String defaultString = element.getPlainAttribute("defaultstring");
        parameter.setDefaults(defaultString);
        log.info("Completed restructuring of Parameter [{}]", id);
        return parameter;
    }
}
