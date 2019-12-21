/*
 * Copyright (c) 2019 Sebastian Boehringer.
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

package edu.horb.dhbw.config.SDMetrics;

import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.XMIReader;
import com.sdmetrics.model.XMITransformations;
import com.sdmetrics.util.XMLParser;
import lombok.NonNull;

/**
 * Utility class that eases the deserialization fo XMI files with
 * {@link XMIReader}.
 */
public final class SDMetricsConfig {
    /**
     * The path to the metamodel as a string. Its the metamodel definition
     * the {@link XMIReader} will use
     */
    private static final String METAMODELURL =
            "src/main/resources/SDMetricsConfig/metamodel2.xml";
    /**
     * The path to the XMI transformations the {@link XMIReader} will use.
     * This will allow it to extract the relevant properties
     */
    private static final String XMITRANSURL =
            "src/main/resources/SDMetricsConfig/xmiTrans2_0.xml";

    private SDMetricsConfig() {

    }

    /**
     * Parses the given xmi-file.
     *
     * @param xmiFile The path to the xmi file which the parser should process
     * @return {@link Model} representing the read file
     *
     * @throws Exception If anything happens during parsing
     */
    public static Model parseXMI(@NonNull final String xmiFile)
            throws Exception {

        XMLParser parser = new XMLParser();
        MetaModel metaModel = new MetaModel();
        parser.parse(METAMODELURL, metaModel.getSAXParserHandler());
        XMITransformations trans = new XMITransformations(metaModel);
        parser.parse(XMITRANSURL, trans.getSAXParserHandler());
        Model model = new Model(metaModel);
        XMIReader xmiReader = new XMIReader(trans, model);
        parser.parse(xmiFile, xmiReader);
        return model;
    }
}
