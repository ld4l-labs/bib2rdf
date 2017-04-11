/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.managers;

import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.AttributeCascader;
import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.CommandLineOptions;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.ConfigurationOverrider;
import org.ld4l.bib2lod.configuration.DefaultBib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.JsonConfigurationFileParser;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.io.InputService;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.InputService.InputServiceException;
import org.ld4l.bib2lod.io.OutputService;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputServiceException;

/**
 * Orchestrates conversion of a directory of files or a single file.
 */
public final class SimpleManager {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Main method: gets a Configuration object and calls the conversion method.
     * 
     * @param args - commandline arguments          
     */
    public static void main(String[] args) {

        LOGGER.info("START CONVERSION.");

        try {
            setup(args);
            convert();
            LOGGER.info("END CONVERSION.");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            LOGGER.error("CONVERSION FAILED TO COMPLETE");
        }
    }

    /**
     * Parse the command line options, read the config file and adjust as
     * necessary. Set up the object factory.
     */
    private static void setup(String[] args) {
        CommandLineOptions commandLine = new CommandLineOptions(args);

        Configuration configuration;
        configuration = new JsonConfigurationFileParser(commandLine)
                .getTopLevelConfiguration();
        configuration = new ConfigurationOverrider(commandLine)
                .override(configuration);
        configuration = new AttributeCascader().cascade(configuration);

        Bib2LodObjectFactory.setFactoryInstance(
                new DefaultBib2LodObjectFactory(configuration));

    }

    /**
     * Converts all of the inputs from the InputService
     * 
     * @param configuration - the program Configuration 
     * @throws ConverterException
     */
    protected static void convert() {

        try {
            Converter converter = Converter.instance();
            InputService inputService = InputService.instance();
            OutputService outputService = OutputService.instance();

            Iterator<InputDescriptor> inputs = inputService.getDescriptors()
                    .iterator();
            while (inputs.hasNext()) {
                try (
                    InputDescriptor input = inputs.next();
                    OutputDescriptor output = outputService
                            .openSink(input.getMetadata())
                ) {
                    converter.convert(input, output);
                } catch (InputServiceException | OutputServiceException
                        | IOException | ConverterException e) {
                    // Log the error and continue to the next input.
                    // TODO We may want a more sophisticated reporting mechanism 
                    // for this type of error.
                    e.printStackTrace();
                }
            }
        } finally {
            // TODO write the report.
        }
    }
}
