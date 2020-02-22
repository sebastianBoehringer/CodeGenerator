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

package edu.horb.dhbw.restructure;

import com.sdmetrics.model.Model;
import edu.horb.dhbw.util.SDMetricsUtil;
import edu.horb.dhbw.datacore.uml.structuredclassifiers.UMLClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TerminateTest {

    @DataProvider(name = "classesReturningThemselves")
    public Object[][] provideClasses() {

        return new Object[][]{
                {"src/test/resources/classdiagrams/Infinite_Mod.xmi"},
                {"src/test/resources/classdiagrams/Infinite_Pap.xmi"}};
    }

    @Test(dataProvider = "classesReturningThemselves", timeOut = 5000L)
    public void testTermination(final String pathToFile)
            throws Exception {

        System.out.println(pathToFile);
        final Model model = SDMetricsUtil.parseXMI(pathToFile);
        final IRestructurerMediator mediator = new IRestructurerMediator();
        mediator.getIRestructurer(UMLClass.class).restructure(model);
    }
}
