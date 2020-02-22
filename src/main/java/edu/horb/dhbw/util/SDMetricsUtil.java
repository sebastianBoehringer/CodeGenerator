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
import com.sdmetrics.model.XMIReader;
import com.sdmetrics.model.XMITransformations;
import com.sdmetrics.util.XMLParser;
import lombok.NonNull;

/**
 * Utility class that eases the deserialization fo XMI files with
 * {@link XMIReader}.
 */
public final class SDMetricsUtil {

    /**
     * Private constructor to prevent instances of the class from being created.
     */
    private SDMetricsUtil() {

    }

    /**
     * Parses the given xmi-file. If you already have a {@link MetaModel}
     * present consider using {@link #parseXMI(MetaModel, String)} as that
     * method does not create a new {@link MetaModel} while this method will.
     *
     * @param xmiFile The path to the xmi file which the parser should process
     * @return {@link Model} representing the read file
     *
     * @throws Exception If anything happens during parsing
     */
    public static Model parseXMI(@NonNull final String xmiFile)
            throws Exception {

        XMLParser parser = new XMLParser();
        MetaModel metaModel = createMetaModel();
        Model model = new Model(metaModel);
        XMIReader xmiReader =
                new XMIReader(createTransformations(parser, metaModel), model);
        parser.parse(xmiFile, xmiReader);
        return model;
    }

    /**
     * @param metaModel The metaModel defining the types to collect
     * @param pathToXMI The path to the xmi file
     * @return A model containing the elements that were collected during
     * parsing
     *
     * @throws Exception If anything goes wrong during the parsing
     */
    public static Model parseXMI(
            @NonNull final MetaModel metaModel, @NonNull final String pathToXMI)
            throws Exception {

        XMLParser parser = new XMLParser();
        Model model = new Model(metaModel);
        XMIReader xmiReader =
                new XMIReader(createTransformations(parser, metaModel), model);
        parser.parse(pathToXMI, xmiReader);
        return model;

    }

    /**
     * @param parser    The parser for the xml files
     * @param metaModel The metamodel to which the transformations apply
     * @return An instantiated XMITransformations object
     *
     * @throws Exception If anything goes wrong during parsing
     */
    private static XMITransformations createTransformations(final XMLParser parser, final MetaModel metaModel)
            throws Exception {

        XMITransformations trans = new XMITransformations(metaModel);
        parser.parse(Config.CONFIG.getTransformationsPath(),
                     trans.getSAXParserHandler());
        return trans;
    }

    /**
     * Creates an instance of the default metamodel for UML 2.x.
     *
     * @return The metaModel
     *
     * @throws Exception If anything goes wrong during xml parsing.
     */
    public static MetaModel createMetaModel()
            throws Exception {

        XMLParser parser = new XMLParser();
        MetaModel metaModel = new MetaModel();
        parser.parse(Config.CONFIG.getMetaModelPath(),
                     metaModel.getSAXParserHandler());
        return metaModel;
    }

}
