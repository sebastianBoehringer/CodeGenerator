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

package edu.horb.dhbw.inputprocessing.restructure;

import com.sdmetrics.model.Model;
import edu.horb.dhbw.util.SDMetricsUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import java.util.HashMap;

public abstract class BaseRestructurerTest {
    protected IRestructurerMediator mediator =
            new RestructurerMediator(new HashMap<>());

    @DataProvider(name = "classdiagramfiles")
    public Object[][] provideClasses() {

        return new Object[][]{
                {"src/test/resources/classdiagrams/Classes_Mod.xmi"},
                {"src/test/resources/classdiagrams/Classes_Pap.xmi"}};
    }

    protected final Model parseXMI(final String pathToFile) {

        System.out.println(String.format("Testing file %s", pathToFile));
        Model model;
        try {
            model = SDMetricsUtil.parseXMI(pathToFile);
        } catch (Exception e) {
            Assert.fail("Encountered an exception while processing xmi", e);
            return null;
        }
        return model;
    }

    @DataProvider(name = "simplestatemachines")
    public Object[][] provideSimpleStateMachines() {

        return new Object[][]{
                {"src/test/resources/statemachines/simpleSM_Mod.xmi"},
                {"src/test/resources/statemachines/simpleSM_Pap.xmi"}};
    }
}
