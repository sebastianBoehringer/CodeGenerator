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
import edu.horb.dhbw.datacore.uml.classification.Operation;
import edu.horb.dhbw.datacore.uml.enums.CallConcurrencyKind;
import edu.horb.dhbw.restructure.BaseRestructurerTest;
import edu.horb.dhbw.restructure.IRestructurer;
import edu.horb.dhbw.restructure.IRestructurerDefImpl;
import edu.horb.dhbw.restructure.IRestructurerMediator;
import edu.horb.dhbw.util.Config;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Properties;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class OperationRestructurerTest extends BaseRestructurerTest {

    @BeforeClass
    public void init() {

        mediator.register(Operation.class, new OperationRestructurer(mediator));
        Config.CONFIG.readInProperties(new Properties());
    }

    @Test(dataProvider = "classdiagramfiles")
    public void onlyOperationRestructuring(final String pathToFile) {

        Model model = parseXMI(pathToFile);

        IRestructurer<Operation> restructurer =
                mediator.getIRestructurer(Operation.class);

        Collection<Operation> operations = restructurer.restructure(model);
        assertEquals(operations.size(), 1,
                     "There should only be one operation");

        for (Operation operation : operations) {
            assertEquals(operation.getName(), "operation",
                         "Name should be operation");

            assertEquals(operation.getConcurrency(),
                         CallConcurrencyKind.SEQUENTIAL,
                         "Default is sequential");
            assertEquals(operation.getIsQuery(), Boolean.FALSE,
                         "Default is false");
            assertEquals(operation.getIsAbstract(), Boolean.FALSE,
                         "Default is false");
            assertEquals(operation.getIsStatic(), Boolean.FALSE,
                         "Default is false");
        }
    }

    @Test
    public void isRegisteredAtMediator() {

        IRestructurerMediator restructurerMediator =
                new IRestructurerMediator();
        IRestructurer<?> restructurer =
                restructurerMediator.getIRestructurer(Operation.class);
        assertFalse(restructurer instanceof IRestructurerDefImpl,
                    "No restructurer registered for Operation.class");
    }
}
