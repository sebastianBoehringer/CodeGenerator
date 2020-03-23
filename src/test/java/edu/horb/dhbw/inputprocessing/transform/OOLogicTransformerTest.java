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

package edu.horb.dhbw.inputprocessing.transform;

import com.sdmetrics.model.Model;
import edu.horb.dhbw.datacore.model.OOLogic;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.util.Config;
import edu.horb.dhbw.util.SDMetricsUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Properties;

public class OOLogicTransformerTest {

    /**
     * Timeout for the tests to prevent an infinite loop when running the tests.
     * Considering the whole model for UML takes roughly 8 seconds, 10
     * seconds for a simple statemachine seem quite generous.
     */
    private static final long TIMEOUT = 10000L;

    @BeforeClass
    private void setUp() {

        Config.CONFIG.readInProperties(new Properties());

    }

    private StateMachine getMachine(String path)
            throws Exception {

        Model model = SDMetricsUtil.parseXMI(path);
        IRestructurerMediator mediator = new IRestructurerMediator();
        return mediator.getIRestructurer(StateMachine.class).restructure(model)
                .get(0);
    }

    @Test(timeOut = TIMEOUT)
    public void canTransformLoop()
            throws Exception {

        StateMachine machine =
                getMachine("src/test/resources/statemachines/loop.xmi");
        ITransformer<StateMachine, OOLogic> logicITransformer =
                new OOLogicTransformer(null);
        logicITransformer.transform(machine);
    }

    @Test(timeOut = TIMEOUT)
    public void canTransformIfWith5Branches()
            throws Exception {

        StateMachine machine =
                getMachine("src/test/resources/statemachines/if.xmi");
        ITransformer<StateMachine, OOLogic> logicITransformer =
                new OOLogicTransformer(null);
        logicITransformer.transform(machine);
    }

    @Test(timeOut = TIMEOUT)
    public void canTransformNestedIfWithDepth2()
            throws Exception {

        StateMachine machine =
                getMachine("src/test/resources/statemachines/NestedIf.xmi");
        ITransformer<StateMachine, OOLogic> logicITransformer =
                new OOLogicTransformer(null);
        logicITransformer.transform(machine);
    }

    @Test(timeOut = TIMEOUT)
    public void canTransformIfWithNestedLoops()
            throws Exception {

        StateMachine machine = getMachine(
                "src/test/resources/statemachines/IfWithNestedLoop.xmi");
        ITransformer<StateMachine, OOLogic> logicITransformer =
                new OOLogicTransformer(null);
        logicITransformer.transform(machine);
    }

    @Test(timeOut = TIMEOUT)
    public void canTransformLoopWithNestedIf()
            throws Exception {

        StateMachine machine = getMachine(
                "src/test/resources/statemachines/LoopWithNestedIf.xmi");
        ITransformer<StateMachine, OOLogic> logicITransformer =
                new OOLogicTransformer(null);
        logicITransformer.transform(machine);
    }

    @Test(timeOut = TIMEOUT)
    public void canTransformNestedLoopsWithDepth2()
            throws Exception {

        StateMachine machine =
                getMachine("src/test/resources/statemachines/NestedLoops.xmi");
        ITransformer<StateMachine, OOLogic> logicITransformer =
                new OOLogicTransformer(null);
        logicITransformer.transform(machine);
    }

}