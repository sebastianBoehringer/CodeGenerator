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
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.enums.AggregationKind;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

public class PropertyRestructurerTest {

    private IRestructurerMediator factory =
            new IRestructurerMediator(new HashMap<>());

    @BeforeClass
    public void init() {

        factory.register(Property.class, new PropertyRestructurer(factory));
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

        IRestructurer<Property> restructurer =
                factory.getIRestructurer(Property.class);
        Collection<Property> properties = restructurer.restructure(model);
        properties = properties.stream()
                .filter(e -> !e.getName().equalsIgnoreCase("exporterversion"))
                .collect(Collectors.toList());

        assertEquals(properties.size(), 1, "There should only be one property");

        for (Property property : properties) {
            assertEquals(property.getName(), "member",
                         "The name of the property should be member");
            assertEquals(property.getVisibility(), VisibilityKind.PUBLIC,
                         "Visibility should be public");
            //TODO test type
            //TODO test multiplicity bounds
            assertEquals(property.getIsUnique(), Boolean.TRUE,
                         "Property should be unique, i. e. this is true");
            assertEquals(property.getIsOrdered(), Boolean.FALSE,
                         "Default is false");
            assertEquals(property.getAggregation(), AggregationKind.NONE,
                         "Default is none");
            assertEquals(property.getIsDerived(), Boolean.FALSE,
                         "Default is false");
            assertEquals(property.getIsComposite(), Boolean.FALSE,
                         "Default is false");
            assertEquals(property.getIsDerivedUnion(), Boolean.FALSE,
                         "Default is false");
            assertEquals(property.getIsID(), Boolean.FALSE, "Default is false");
            assertEquals(property.getIsReadOnly(), Boolean.FALSE,
                         "Default is false");
            assertEquals(property.getIsStatic(), Boolean.FALSE,
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
