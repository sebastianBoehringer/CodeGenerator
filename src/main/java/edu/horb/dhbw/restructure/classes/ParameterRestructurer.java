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
import edu.horb.dhbw.datacore.uml.enums.ParameterDirectionKind;
import edu.horb.dhbw.datacore.uml.enums.ParameterEffectKind;
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.restructure.RestructurerBase;
import lombok.NonNull;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
            return ALREADY_PROCESSED.get(id);
        }
        Parameter parameter = new Parameter();

        parameter.setId(id);

        String name = element.getPlainAttribute("name");
        parameter.setName(name);

        //TODO contexts

        String ordered = element.getPlainAttribute("ordered");
        parameter.setIsOrdered(Boolean.valueOf(ordered));

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

        String lower = element.getPlainAttribute("lower");
        if (lower != null && !("".equals(lower))) {
            parameter.setLower(Integer.parseInt(lower));
        }
        String upper = element.getPlainAttribute("upper");
        if (upper != null && !("".equals(upper))) {
            parameter.setUpper(new UnlimitedNatural(upper));
        }

        //TODO lowerValue

        //TODO upperValue


        //TODO type

        String kind = element.getPlainAttribute("kind");
        ParameterDirectionKind directionKind =
                StringUtils.isEmpty(kind) ? ParameterDirectionKind.IN
                                          : ParameterDirectionKind.from(kind);
        parameter.setDirection(directionKind);

        //TODO parametertype

        String effect = element.getPlainAttribute("effect");
        ParameterEffectKind effectKind =
                StringUtils.isEmpty(effect) ? ParameterEffectKind.UNDEFINED
                                            : ParameterEffectKind.from(effect);
        parameter.setEffect(effectKind);

        String exception = element.getPlainAttribute("exception");
        parameter.setIsException(Boolean.valueOf(exception));

        String stream = element.getPlainAttribute("stream");
        parameter.setIsStream(Boolean.valueOf(stream));

        //TODO default

        ALREADY_PROCESSED.put(parameter.getId(), parameter);
        return parameter;
    }

    @Override
    public Optional<Parameter> getProcessed(@NonNull final String id) {

        return Optional.ofNullable(ALREADY_PROCESSED.get(id));
    }
}
