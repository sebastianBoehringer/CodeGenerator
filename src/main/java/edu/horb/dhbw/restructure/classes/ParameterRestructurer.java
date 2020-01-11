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
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.commonstructure.Type;
import edu.horb.dhbw.datacore.uml.enums.ParameterDirectionKind;
import edu.horb.dhbw.datacore.uml.enums.ParameterEffectKind;
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public final class ParameterRestructurer extends RestructurerBase<Parameter> {

    /**
     * A map holding all the {@link Parameter}s that have already been
     * processed. This maps from their xmi id to the actual instance.
     * The map is not synchronized, thus the class is most likely not
     * threadsafe.
     */
    private static final Map<String, Parameter> ALREADY_PROCESSED =
            new HashMap<>();

    /**
     * Constructor delegating to
     * {@link RestructurerBase#RestructurerBase(IRestructurerMediator, String)}.
     *
     * @param iRestructurerMediator The mediator responsible for providing
     *                              the other {@link IRestructurer}s
     */
    public ParameterRestructurer(final IRestructurerMediator iRestructurerMediator) {

        super(iRestructurerMediator, "parameter");
    }

    @Override
    public Parameter restructure(@NonNull final ModelElement element) {

        String id = element.getXMIID();
        if (ALREADY_PROCESSED.containsKey(id)) {
            log.info("Found id [{}] in cache, loading parameter from cache",
                     id);
            return ALREADY_PROCESSED.get(id);
        }
        Parameter parameter = new Parameter();
        parameter.setId(id);
        ALREADY_PROCESSED.put(id, parameter);

        log.info("Processing name for parameter [{}]", id);
        String name = element.getPlainAttribute("name");
        parameter.setName(name);

        //TODO contexts

        log.info("Processing ordered for parameter [{}]", id);
        String ordered = element.getPlainAttribute("ordered");
        parameter.setIsOrdered(Boolean.valueOf(ordered));

        log.info("Processing unique for parameter [{}]", id);
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

        log.info("Processing lower for parameter [{}]", id);
        String lower = element.getPlainAttribute("lower");
        if (!StringUtils.isEmpty(lower)) {
            parameter.setLower(Integer.parseInt(lower));
        }
        log.info("Processing upper for parameter [{}]", id);
        String upper = element.getPlainAttribute("upper");
        if (!StringUtils.isEmpty(upper)) {
            parameter.setUpper(new UnlimitedNatural(upper));
        }

        log.info("Processing lowerValue for parameter [{}]", id);
        ModelElement lowerValue = element.getRefAttribute("lowerValue");
        parameter.setLowerValue(
                delegateRestructuring(lowerValue, ValueSpecification.class));

        log.info("Processing upperValue for parameter [{}]", id);
        ModelElement upperValue = element.getRefAttribute("upperValue");
        parameter.setUpperValue(
                delegateRestructuring(upperValue, ValueSpecification.class));

        log.info("Processing kind for parameter [{}]", id);
        String kind = element.getPlainAttribute("kind");
        ParameterDirectionKind directionKind =
                StringUtils.isEmpty(kind) ? ParameterDirectionKind.IN
                                          : ParameterDirectionKind.from(kind);
        parameter.setDirection(directionKind);

        log.info("Processing parametertype for parameter [{}]", id);
        ModelElement type = element.getRefAttribute("parametertype");
        parameter.setType(delegateRestructuring(type, Type.class));

        log.info("Processing effect for parameter [{}]", id);
        String effect = element.getPlainAttribute("effect");
        ParameterEffectKind effectKind =
                StringUtils.isEmpty(effect) ? ParameterEffectKind.UNDEFINED
                                            : ParameterEffectKind.from(effect);
        parameter.setEffect(effectKind);

        log.info("Processing exception for parameter [{}]", id);
        String exception = element.getPlainAttribute("exception");
        parameter.setIsException(Boolean.valueOf(exception));

        log.info("Processing stream for parameter [{}]", id);
        String stream = element.getPlainAttribute("stream");
        parameter.setIsStream(Boolean.valueOf(stream));

        log.info("Processing default for parameter [{}]", id);
        ModelElement defaults = element.getRefAttribute("default");
        parameter.setDefaultValue(
                delegateRestructuring(defaults, ValueSpecification.class));

        log.info("Processing defaultstring for parameter [{}]", id);
        String defaultString = element.getPlainAttribute("defaultstring");
        parameter.setDefaults(defaultString);

        return parameter;
    }

    @Override
    public Optional<Parameter> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }

    @Override
    public void cleanCache() {

        ALREADY_PROCESSED.clear();
    }
}
