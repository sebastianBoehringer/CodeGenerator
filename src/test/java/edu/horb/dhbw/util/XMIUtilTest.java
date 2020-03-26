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

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class XMIUtilTest {

    @Test
    public void testTrimNamespace() {

        assertEquals(XMIUtil.trimNamespace("uml:LoremIpsum"), "LoremIpsum",
                     "Should trim uml: at beginning");
        assertEquals(XMIUtil.trimNamespace("Blinduml:Text"), "BlindText",
                     "should trim uml: in middle of word");
        assertEquals(XMIUtil.trimNamespace("xmi:test"), "xmi:test",
                     "shouldnt trim other namespace");
        assertEquals(XMIUtil.trimNamespace("uml:uml:"), "uml:",
                     "should only trim once");
        assertEquals(XMIUtil.trimNamespace("Uml:"), "Uml:",
                     "should not trim an in any form capitalized uml:");

    }
}