package edu.horb.dhbw.config.jackson;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JacksonConfig {

    public static XmlMapper xmlMapper() {

        JacksonXmlModule module = new JacksonXmlModule();

        module.setDefaultUseWrapper(false);
        return new XmlMapper(module);
    }
}
