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

import com.sdmetrics.model.Model;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.enums.ParameterDirectionKind;
import edu.horb.dhbw.datacore.uml.enums.ParameterEffectKind;
import edu.horb.dhbw.inputprocessing.restructure.BaseRestructurerTest;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerDefImpl;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.inputprocessing.restructure.RestructurerMediator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class ParameterRestrucuturerTest extends BaseRestructurerTest {

    @BeforeClass
    public void init() {

        mediator.register(Parameter.class, new ParameterRestructurer(mediator));
    }

    @Test(dataProvider = "classdiagramfiles")
    public void onlyParameterRestructuring(final String pathToFile) {

        Model model = parseXMI(pathToFile);

        List<Parameter> parameters =
                mediator.getIRestructurer(Parameter.class).restructure(model);
        assertEquals(parameters.size(), 2, "Expecting two parameters");

        List<Parameter> inputs = parameters.stream()
                .filter(p -> p.getDirection().equals(ParameterDirectionKind.IN))
                .collect(Collectors.toList());
        assertEquals(inputs.size(), 1, "Only one input parameter expected");

        List<Parameter> returns = parameters.stream()
                .filter(p -> p.getDirection()
                        .equals(ParameterDirectionKind.RETURN))
                .collect(Collectors.toList());
        assertEquals(returns.size(), 1, "Only one return parameter expected");

        for (Parameter aReturn : returns) {

            assertEquals(aReturn.getEffect(), ParameterEffectKind.UNDEFINED,
                         "Neither Modelio nor Papyrus seem to serialize "
                                 + "CREATE on a return param");
            testDefaultValues(aReturn);
        }
        for (Parameter input : inputs) {
            assertEquals(input.getName(), "p1",
                         "Input parameter should be named p1");
            if (pathToFile.contains("Pap")) {
                assertEquals(input.getEffect(), ParameterEffectKind.READ,
                             "Papyrus serializes READ");
            } else {
                assertEquals(input.getEffect(), ParameterEffectKind.UNDEFINED,
                             "Modelio seemingly does not serialize "
                                     + "effectkinds at all");
            }
            testDefaultValues(input);
        }
    }

    private void testDefaultValues(final Parameter parameter) {

        assertEquals(parameter.getIsStream(), Boolean.FALSE,
                     "Default is false");
        assertEquals(parameter.getIsException(), Boolean.FALSE,
                     "Default is false");
        assertEquals(parameter.getIsOrdered(), Boolean.FALSE,
                     "Default is false");
        assertEquals(parameter.getIsUnique(), Boolean.TRUE, "Default is true");
    }


    @Test
    public void isRegisteredAtMediator() {

        IRestructurerMediator restructurerMediator = new RestructurerMediator();
        IRestructurer<?> restructurer =
                restructurerMediator.getIRestructurer(Parameter.class);
        assertFalse(restructurer instanceof IRestructurerDefImpl,
                    "No restructurer registered for Parameter.class");
    }
}
