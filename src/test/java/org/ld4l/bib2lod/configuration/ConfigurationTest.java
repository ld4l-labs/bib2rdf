package org.ld4l.bib2lod.configuration;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.testing.AbstractTestClass;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigurationTest extends AbstractTestClass {

    private static JsonNode CONFIG = null;

    @Before
    public void setUpConfiguration() throws Exception {

        File configFile = new File("src/test/resources/config/config.json");
        ObjectMapper mapper = new ObjectMapper();
        CONFIG = mapper.readTree(configFile);
    }

    @Test
    public void setLocalNamespace() {

    }

}
