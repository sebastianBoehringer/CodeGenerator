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

package edu.horb.dhbw.restructure.statemachines;

import com.sdmetrics.model.Model;
import edu.horb.dhbw.datacore.uml.enums.TransitionKind;
import edu.horb.dhbw.datacore.uml.statemachines.Region;
import edu.horb.dhbw.datacore.uml.statemachines.State;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.datacore.uml.statemachines.Transition;
import edu.horb.dhbw.restructure.BaseRestructurerTest;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerDefImpl;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class StateMachineRestructurerTest extends BaseRestructurerTest {

    @BeforeClass
    public void init() {

        mediator.register(StateMachine.class,
                          new StateMachineRestructurer(mediator));
        mediator.register(State.class, new StateRestructurer(mediator));
        mediator.register(Region.class, new RegionRestructurer(mediator));
        mediator.register(Transition.class,
                          new TransitionRestructurer(mediator));
    }

    @Test(dataProvider = "simplestatemachines")
    public void restructureSimpleStatemachineCompletely(final String pathToFile) {

        Model model = parseXMI(pathToFile);
        List<StateMachine> machines =
                mediator.getIRestructurer(StateMachine.class)
                        .restructure(model);
        assertEquals(machines.size(), 1,
                     "There should only be a single statemachine");
        StateMachine machine = machines.get(0);
        assertEquals(machine.getName(), "sm",
                     "Statemachine should be named sm");

        List<Region> regions = machine.getRegion();
        assertEquals(regions.size(), 1, "There should be a single region in "
                + "this statemachine");

        assertTrue(machine.getIsReentrant(),
                   "StateMachine should be reentrant");

        List<Transition> transitions = regions.get(0).getTransition();
        List<State> states = regions.get(0).getSubvertex();

        assertEquals(transitions.size(), 2, "There should be two transitions."
                + " One from init to intermediate and one from intermediate "
                + "to final");

        assertEquals(states.size(), 3, "There should be 3 states. initial, "
                + "intermediate and final");

        State initial = null;
        State intermediate = null;
        State end = null;
        for (State state : states) {
            switch (state.getKind()) {
                case STATE:
                    testName(state, "Intermediate");
                    intermediate = state;
                    break;
                case INITIAL:
                    testName(state, "Initial State");
                    initial = state;
                    break;
                case FINAL:
                    testName(state, "Final State");
                    end = state;
                    break;
                default:
                    fail("There should not be any other kind of state in the "
                                 + "diagram");
            }
        }
        assertNotNull(initial, "One state should have had type INITIAL");
        assertNotNull(intermediate, "One state should have had type STATE");
        assertNotNull(end, "One state should have had type FINAL");

        Transition first = null;
        Transition second = null;
        for (Transition transition : transitions) {
            assertEquals(transition.getKind(), TransitionKind.EXTERNAL,
                         "Should only have external transitions");
            assertEquals(transition.getName(), "",
                         "Transitions should have empty string for name");
            if (transition.getSource().equals(initial)) {
                first = transition;
            } else if (transition.getSource().equals(intermediate)) {
                second = transition;
            } else {
                fail("No transition should start from a state different to "
                             + "intermediate and initial");
            }
        }

        assertNotNull(first, "One transition should start from initial state");
        assertNotNull(second, "One transition should start from intermediate "
                + "state");

        assertEquals(first.getTarget(), intermediate, "first should "
                + "transition from initial to intermediate");
        assertEquals(second.getTarget(), end, "second should transition from "
                + "intermediate to final");
    }

    private void testName(final State toTest, final String name) {

        assertEquals(toTest.getName(), name,
                     String.format("name of the state should be %s", name));
    }

    @Test
    public void isRegisteredAtMediator() {

        IRestructurerMediator restructurerMediator =
                new IRestructurerMediator();
        IRestructurer<?> restructurer =
                restructurerMediator.getIRestructurer(StateMachine.class);
        assertFalse(restructurer instanceof IRestructurerDefImpl,
                    "No restructurer registered for StateMachine.class");
    }
}
