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

package edu.horb.dhbw;

import edu.horb.dhbw.exception.CodeGenerationException;
import edu.horb.dhbw.util.Config;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static org.testng.Assert.fail;

public class CodeGeneratorTest {

    @Test
    public void smokeTest() {

        CodeGenerator generator = new CodeGenerator(
                Paths.get("src/test/resources/test.properties"));
        try {
            generator.generateCode(Paths.get(
                    "src/test/resources/classdiagrams/Classes_Mod.xmi"),
                                   Config.CONFIG.getLanguage());
        } catch (CodeGenerationException e) {
            e.printStackTrace();
            fail("Generation of code failed");
        }
    }
}
