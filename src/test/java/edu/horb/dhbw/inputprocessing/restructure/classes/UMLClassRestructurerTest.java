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
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.classification.Parameter;
import edu.horb.dhbw.datacore.uml.classification.Property;
import edu.horb.dhbw.datacore.uml.enums.VisibilityKind;
import edu.horb.dhbw.datacore.uml.primitivetypes.UnlimitedNatural;
import edu.horb.dhbw.datacore.uml.simpleclassifiers.PrimitiveType;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import edu.horb.dhbw.datacore.uml.values.LiteralInteger;
import edu.horb.dhbw.datacore.uml.values.LiteralUnlimitedNatural;
import edu.horb.dhbw.datacore.uml.values.ValueSpecification;
import edu.horb.dhbw.inputprocessing.restructure.BaseRestructurerTest;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.NoopRestructurer;
import edu.horb.dhbw.inputprocessing.restructure.IRestructurerMediator;
import edu.horb.dhbw.inputprocessing.restructure.RestructurerMediator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UMLClassRestructurerTest extends BaseRestructurerTest {

    @BeforeClass
    public void init() {

        mediator.register(UMLClass.class, new UMLClassRestructurer(mediator));
    }

    @Test(dataProvider = "classdiagramfiles")
    public void onlyClassRestructuring(final String pathToFile) {

        Model model = parseXMI(pathToFile);

        IRestructurer<UMLClass> restructurer =
                mediator.getIRestructurer(UMLClass.class);
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

    @Test(dependsOnMethods = {"onlyClassRestructuring"},
          dataProvider = "classdiagramfiles")
    public void restructureSimpleClass(final String pathToFile) {

        mediator = new RestructurerMediator();
        mediator.readyForNextRestructuring();
        Model model = parseXMI(pathToFile);

        List<UMLClass> classes =
                mediator.getIRestructurer(UMLClass.class).restructure(model);
        assertEquals(classes.size(), 1, "should only be one class in the file");
        UMLClass theClass = classes.get(0);

        List<Property> properties = theClass.getOwnedAttribute();
        assertEquals(properties.size(), 1,
                     "class should have a single " + "property");

        Property property = properties.get(0);
        assertEquals(property.getType().getName(), "String",
                     "Property should have type string");
        assertTrue(property.getType() instanceof PrimitiveType,
                   "Property should have primitive type");

        ValueSpecification lowerSpec = property.getLowerValue();
        assertTrue(lowerSpec instanceof LiteralInteger);
        assertEquals(((LiteralInteger) lowerSpec).getValue(),
                     Integer.valueOf(1),
                     "Lower multiplicity end should be one");
        ValueSpecification upperSpec = property.getUpperValue();
        assertTrue(upperSpec instanceof LiteralUnlimitedNatural);
        assertEquals(((LiteralUnlimitedNatural) upperSpec).getValue(),
                     UnlimitedNatural.ONE);

        List<Operation> operations = theClass.getOwnedOperation();
        assertEquals(operations.size(), 1,
                     "class should have a single operation");
        Operation operation = operations.get(0);

        List<Parameter> parameters = operation.getOwnedParameter();

        for (Parameter parameter : parameters) {
            assertEquals(parameter.getType().getName(), "String",
                         "Property should have type string");
            assertTrue(parameter.getType() instanceof PrimitiveType,
                       "Property should have primitive type");

            ValueSpecification lowerSpecOp = parameter.getLowerValue();
            assertTrue(lowerSpecOp instanceof LiteralInteger);
            assertEquals(((LiteralInteger) lowerSpecOp).getValue(),
                         Integer.valueOf(1),
                         "Lower multiplicity end should be one");
            ValueSpecification upperSpecOp = parameter.getUpperValue();
            assertTrue(upperSpecOp instanceof LiteralUnlimitedNatural);
            assertEquals(((LiteralUnlimitedNatural) upperSpecOp).getValue(),
                         UnlimitedNatural.ONE);
        }
    }

    @Test
    public void isRegisteredAtMediator() {

        IRestructurerMediator restructurerMediator = new RestructurerMediator();
        IRestructurer<?> restructurer =
                restructurerMediator.getIRestructurer(UMLClass.class);
        assertFalse(restructurer instanceof NoopRestructurer,
                    "No restructurer registered for UMLClass.class");
    }
}
