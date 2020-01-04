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

import com.sdmetrics.model.Model;
import edu.horb.dhbw.config.SDMetrics.SDMetricsConfig;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class UMLClassRestructurerTest {

    private IRestructurerMediator factory =
            new IRestructurerMediator(new HashMap<>());

    @BeforeClass
    public void init() {

        factory.register(UMLClass.class, new UMLClassRestructurer(factory));
    }

    @Test(dataProvider = "classdiagramfiles")
    public void onlyClassRestructuring(final String pathToFile) {
        System.out.println(String.format("Testing file %s", pathToFile));
        Model model;
        try {
            model = SDMetricsConfig.parseXMI(pathToFile);
        } catch (Exception e) {
            Assert.fail("Encountered an exception while processing xmi", e);
            return;
        }

        IRestructurer<UMLClass> restructurer =
                factory.getIRestructurer(UMLClass.class);
        Collection<UMLClass> classes = restructurer.restructure(model);
        assertEquals(classes.size(), 1,
                     "There should have been exactly one class");

        for (UMLClass aClass : classes) {
            assertEquals(aClass.getName(), "Class",
                         "The name of the class should have been [Class]");
            assertEquals(aClass.getVisibility(), VisibilityKind.PUBLIC,
                         "Default visibility is public");
            assertEquals(aClass.getIsAbstract(), Boolean.FALSE,
                         "Default is false");
            assertEquals(aClass.getIsActive(), Boolean.FALSE,
                         "Default is false");
            assertEquals(aClass.getIsFinalSpecialization(), Boolean.FALSE,
                         "Default is false");
        }

    }

    @DataProvider(name = "classdiagramfiles")
    public Object[][] provideClasses() {

        return new Object[][]{
                {"src/test/resources/classdiagrams/Classes_Mod.xmi"},
                {"src/test/resources/classdiagrams/Classes_Pap.xmi"}};
    }
}
