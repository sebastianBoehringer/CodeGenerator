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
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.enums.AggregationKind;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.inputprocessing.restructure.BaseRestructurerTest;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.inputprocessing.restructure.NoopRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.RestructurerMediator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class PropertyRestructurerTest extends BaseRestructurerTest {

    @BeforeClass
    public void init() {

        mediator.register(Property.class, new PropertyRestructurer(mediator));
    }

    @Test(dataProvider = "classdiagramfiles")
    public void onlyClassRestructuring(final String pathToFile) {

        Model model = parseXMI(pathToFile);

        IRestructurer<Property> restructurer =
                mediator.getIRestructurer(Property.class);
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

    @Test
    public void isRegisteredAtMediator() {

        IRestructurerMediator restructurerMediator = new RestructurerMediator();
        IRestructurer<?> restructurer =
                restructurerMediator.getIRestructurer(Property.class);
        assertFalse(restructurer instanceof NoopRestructurer,
                    "No restructurer registered for Property.class");
    }
}
