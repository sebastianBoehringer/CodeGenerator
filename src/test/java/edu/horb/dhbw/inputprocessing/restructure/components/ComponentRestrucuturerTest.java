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

import com.sdmetrics.model.Model;
import edu.horb.dhbw.datacore.uml.classification.Classifier;
import edu.horb.dhbw.datacore.uml.commonstructure.PackageableElement;
import edu.horb.dhbw.datacore.uml.statemachines.StateMachine;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.Component;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.ComponentRealization;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClassImpl;
import edu.horb.dhbw.inputprocessing.restructure.BaseRestructurerTest;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerDefImpl;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.inputprocessing.restructure.RestructurerMediator;
import edu.horb.dhbw.util.Config;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public final class ComponentRestrucuturerTest extends BaseRestructurerTest {

    @BeforeClass
    private void init() {

        Config.CONFIG.readInProperties(new Properties());
    }

    @Test
    public void isRegisteredAtMediator() {

        IRestructurerMediator restructurerMediator = new RestructurerMediator();
        IRestructurer<?> restructurer =
                restructurerMediator.getIRestructurer(StateMachine.class);
        assertFalse(restructurer instanceof IRestructurerDefImpl,
                    "No restructurer registered for Component.class");
    }

    @Test
    public void canRestructureBasicComponent() {

        Model model = parseXMI(
                "src/test/resources/componentdiagrams/SimpleComponent"
                        + ".xmi");
        IRestructurerMediator mediator = new RestructurerMediator();
        List<Component> components =
                mediator.getIRestructurer(Component.class).restructure(model);
        assertEquals(components.size(), 1,
                     "There should only be 1 component in this");

        Component component = components.get(0);
        assertEquals(component.getName(), "Component",
                     "Component should be named Component");

        assertEquals(component.getPackagedElement().size(), 1,
                     "Component should package a single element");
        PackageableElement element = component.getPackagedElement().get(0);
        assertEquals(element.getName(), "InnerRealizingClass");
        assertEquals(UMLClassImpl.class, element.getClass(), "packagedElement"
                + " should be a class and no subtype of it either");

        assertEquals(component.getRealization().size(), 2, "Component should "
                + "be realized by two non packaged elements");
        List<String> possibleRealizingClassifierNames =
                Arrays.asList("RealizingClassA", "RealizingClassB");
        for (ComponentRealization realization : component.getRealization()) {
            assertEquals(realization.getRealizingClassifier().size(), 1,
                         "Only a single classifier should participate in the "
                                 + "realization of the component");
            Classifier classifier = realization.getRealizingClassifier().get(0);
            assertEquals(classifier.getClass(), UMLClassImpl.class,
                         "Classifier should be an instance of UMLClass and "
                                 + "not any of its subtypes");
            assertTrue(possibleRealizingClassifierNames
                               .contains(classifier.getName()), String.format(
                    "Classifier was named %s which was not expected",
                    classifier.getName()));
        }

    }

}
