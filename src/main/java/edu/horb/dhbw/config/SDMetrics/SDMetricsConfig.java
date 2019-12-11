package edu.horb.dhbw.config.SDMetrics;

import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.XMIReader;
import com.sdmetrics.model.XMITransformations;
import com.sdmetrics.util.XMLParser;

public class SDMetricsConfig {
    private final static String METAMODELURL = "src/main/resources/SDMetricsConfig/metamodel2.xml";
    // metamodel definition to use
    private final static String XMITRANSURL = "src/main/resources/SDMetricsConfig/xmiTrans2_0" +
            ".xml";   // XMI tranformations to use

    public static Model parseXMI(String xmiFile)
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
