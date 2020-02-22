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
package edu.horb.dhbw.util;

import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import lombok.NonNull;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class SDMetricsUtilTest {

    private MetaModel metaModel;

    @BeforeClass
    public void init()
            throws Exception {

        metaModel = SDMetricsUtil.createMetaModel();
    }

    @Test(dataProvider = "classes")
    public void readInUMLClass(final String pathToFile) {

        System.out.println(String.format("Testing file [%s] now", pathToFile));
        Model model;
        try {
            model = SDMetricsUtil.parseXMI(metaModel, pathToFile);
        } catch (Exception e) {
            Assert.fail("Encountered an exception while processing xmi", e);
            return;
        }
        List<ModelElement> classes =
                model.getElements(metaModel.getType("class"));
        assertEquals(classes.size(), 1,
                     "Only one class should have been found");
        ModelElement clazz = classes.get(0);
        assertEquals(clazz.getName(), "Class",
                     "The umlclass should have been named Class");
        //Default value for the attribute is public. Thus the check via RegEx
        String pattern = "(public|)";
        assertTrue(Pattern.matches(pattern,
                                   clazz.getPlainAttribute("visibility")
                                           .toLowerCase()),
                   "Class should have public visibility");
        assertEquals(clazz.getSetAttribute("ownedattributes").size(), 1,
                     "Class should have one attribute only");
        List<ModelElement> properties =
                model.getElements(metaModel.getType("property"));

        ModelElement property =
                properties.stream().filter(e -> e.getName().equals("member"))
                        .findFirst().orElse(null);
        assertNotNull(property, "There should be a property with name member");
        assertTrue(Pattern.matches(pattern,
                                   property.getPlainAttribute("visibility")
                                           .toLowerCase()),
                   "Property should be public");
        ModelElement propertyType = property.getRefAttribute("type");
        assertIsPrimitiveType(propertyType);
        assertTypeIsPrimitiveString(propertyType);

        Collection<?> ownedOperations =
                clazz.getSetAttribute("ownedoperations");
        assertEquals(ownedOperations.size(), 1,
                     "Class should only own a single operation.");
        List<ModelElement> operations =
                model.getElements(metaModel.getType("operation"));
        assertEquals(operations.size(), 1,
                     "Only one operation should be declared");
        ModelElement operation = operations.get(0);
        assertEquals(operation.getName(), "operation",
                     "The operation should be named operation.");
        assertTrue(Pattern.matches(pattern,
                                   operation.getPlainAttribute("visibility")),
                   "Operation should be public");

        Collection<?> ownedParameters =
                operation.getSetAttribute("ownedparameters");
        assertEquals(ownedParameters.size(), 2,
                     "Operation should own 2 parameters");
        List<ModelElement> parameters =
                model.getElements(metaModel.getType("parameter"));
        assertEquals(parameters.size(), 2,
                     "Only 2 parameters should be declared");
        ModelElement returnParam = parameters.stream()
                .filter(e -> e.getPlainAttribute("kind").equals("return"))
                .findFirst().orElse(null);
        assertNotNull(returnParam, "One parameter should be of kind return");
        ModelElement inParam = parameters.stream().filter(e -> Pattern
                .matches("(in|)", e.getPlainAttribute("kind"))).findFirst()
                .orElse(null);
        assertNotNull(inParam, "One parameter should be of kind in");
        assertEquals(inParam.getName(), "p1",
                     "Input parameter should be named p1");

        ModelElement inType = inParam.getRefAttribute("type");
        assertIsPrimitiveType(inType);
        assertTypeIsPrimitiveString(inType);
        ModelElement returnType = returnParam.getRefAttribute("type");
        assertIsPrimitiveType(returnType);
        assertTypeIsPrimitiveString(returnType);


    }

    @DataProvider(name = "classes")
    public Object[][] provideClasses() {

        return new Object[][]{
                {"src/test/resources/classdiagrams/Classes_Mod.xmi"},
                {"src/test/resources/classdiagrams/Classes_Pap.xmi"}};
    }

    private void assertIsPrimitiveType(@NonNull final ModelElement e) {

        assertEquals(e.getType().getName(), "primitivetype",
                     "e should be a primitive type");
    }

    private void assertTypeIsPrimitiveString(@NonNull final ModelElement e) {

        assertEquals(e.getPlainAttribute("href").replaceFirst(".*#", "")
                             .toLowerCase(), "string",
                     "The primitive type should be string");
    }
}
