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
import edu.horb.dhbw.datacore.model.ChoiceStatement;
import edu.horb.dhbw.datacore.model.IStatement;
import edu.horb.dhbw.datacore.model.LoopStatement;
import edu.horb.dhbw.datacore.model.OOLogic;
import edu.horb.dhbw.datacore.model.OpaqueStatement;
import edu.horb.dhbw.datacore.model.Pair;
import edu.horb.dhbw.datacore.model.StatementKind;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.util.Config;
import edu.horb.dhbw.util.SDMetricsUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Properties;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

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

    @Test()
    public void canTransformIfWithNestedLoop()
            throws Exception {

        StateMachine machine = getMachine(
                "src/test/resources/statemachines/IfWithNestedLoop.xmi");
        ITransformer<StateMachine, OOLogic> logicITransformer =
                new OOLogicTransformer(null);
        OOLogic logic = logicITransformer.transform(machine);
        assertNotNull(logic);
        assertEquals(logic.getStatements().size(), 4,
                     "There should be " + "4 statements on top level");
        assertEquals(logic.getStatements().get(0).getStatementType(),
                     StatementKind.OPAQUE, "First statement should be opaque");
        assertEquals(logic.getStatements().get(1).getStatementType(),
                     StatementKind.OPAQUE, "Second statement should be opaque");
        assertEquals(logic.getStatements().get(2).getStatementType(),
                     StatementKind.CHOICE, "Third statement should be choice");
        assertEquals(logic.getStatements().get(3).getStatementType(),
                     StatementKind.OPAQUE, "Fourth statement should be opaque");
        assertEquals(
                ((OpaqueStatement) logic.getStatements().get(0)).getStatement(),
                "0");
        assertEquals(
                ((OpaqueStatement) logic.getStatements().get(1)).getStatement(),
                "1");
        assertEquals(
                ((OpaqueStatement) logic.getStatements().get(3)).getStatement(),
                "5");
        ChoiceStatement statement =
                (ChoiceStatement) logic.getStatements().get(2);
        assertEquals(statement.getBranches().size(), 2);
        Pair<String, List<IStatement>> branchA = statement.getBranches().get(0);
        Pair<String, List<IStatement>> branchB = statement.getBranches().get(1);
        assertEquals(branchA.first(), "4 >= a");
        assertEquals(branchA.second().size(), 1);
        assertEquals(branchA.second().get(0).getStatementType(),
                     StatementKind.LOOP);
        LoopStatement loop = (LoopStatement) branchA.second().get(0);
        assertEquals(loop.getCondition(), "0 <= handy <= *");
        assertEquals(loop.getStatements().size(), 3);
        assertEquals(loop.getStatements().get(0).getStatementType(),
                     StatementKind.OPAQUE);
        assertEquals(loop.getStatements().get(1).getStatementType(),
                     StatementKind.OPAQUE);
        assertEquals(loop.getStatements().get(2).getStatementType(),
                     StatementKind.OPAQUE);
        assertEquals(
                ((OpaqueStatement) loop.getStatements().get(0)).getStatement(),
                "1.2");
        assertEquals(
                ((OpaqueStatement) loop.getStatements().get(1)).getStatement(),
                "1.3");
        assertEquals(
                ((OpaqueStatement) loop.getStatements().get(2)).getStatement(),
                "1.4");

        assertEquals(branchB.first(), "4 < a");
        assertEquals(branchB.second().size(), 1);
        assertEquals(branchB.second().get(0).getStatementType(),
                     StatementKind.LOOP);
         loop = (LoopStatement) branchB.second().get(0);
        assertEquals(loop.getCondition(), "true");
        assertEquals(loop.getStatements().size(), 4);
        assertEquals(loop.getStatements().get(0).getStatementType(),
                     StatementKind.OPAQUE);
        assertEquals(loop.getStatements().get(1).getStatementType(),
                     StatementKind.OPAQUE);
        assertEquals(loop.getStatements().get(2).getStatementType(),
                     StatementKind.OPAQUE);
        assertEquals(loop.getStatements().get(3).getStatementType(),
                     StatementKind.OPAQUE);
        assertEquals(
                ((OpaqueStatement) loop.getStatements().get(0)).getStatement(),
                "2.1");
        assertEquals(
                ((OpaqueStatement) loop.getStatements().get(1)).getStatement(),
                "2.2");
        assertEquals(
                ((OpaqueStatement) loop.getStatements().get(2)).getStatement(),
                "2.3");
        assertEquals(
                ((OpaqueStatement) loop.getStatements().get(3)).getStatement(),
                "2.4");


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

    //Fails since the transformer treats the JUNCTION as the beginning of a
    // loop. Since it has less than 2 outgoing/incoming edges
    // handleNonSimpleState cannot decide what to do and throws a
    // IllegalArgumentException instead
    @Test(timeOut = TIMEOUT)
    public void canTransformLoopFollowedByImmediateIf()
            throws Exception {

        StateMachine machine =
                getMachine("src/test/resources/statemachines/LoopIntoIf.xmi");
        ITransformer<StateMachine, OOLogic> logicITransformer =
                new OOLogicTransformer(null);
        logicITransformer.transform(machine);
    }

}