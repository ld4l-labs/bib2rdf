/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;
import org.ld4l.bib2lod.configuration.ConfigurationNode;
import org.ld4l.bib2lod.io.InputService.InputMetadata;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputServiceException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Check how the service reacts to bad configurations, and good ones.
 */
public class FileOutputServiceTest extends AbstractTestClass {

    private static final String NTRIPLES = "N-TRIPLES";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private FileOutputService service;
    private OutputDescriptor output;

    @After
    public void cleanUp() throws IOException, OutputServiceException {
        if (output != null) {
            output.close();
        }
    }

    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void destinationNotSupplied_throwsExeption() throws IOException {
        createServiceAndGetDescriptor(null, NTRIPLES, metadata("test"));
    }

    @Test(expected = ConfigurationException.class)
    public void destinationDoesntExist_throwsException() throws IOException {
        File file = new File(folder.getRoot().getCanonicalPath(),
                "doesnt_exist");
        createServiceAndGetDescriptor(file, NTRIPLES, metadata("test"));
    }

    @Test(expected = ConfigurationException.class)
    public void destinationNotADirectory_throwsException() throws IOException {
        File file = folder.newFile("not_a_directory");
        createServiceAndGetDescriptor(file, NTRIPLES, metadata("test"));
    }

    @Test(expected = ConfigurationException.class)
    public void destinationNotWriteable_throwsException() throws IOException {
        File dest = folder.newFolder("cant_write");
        dest.setWritable(false);
        createServiceAndGetDescriptor(dest, NTRIPLES, metadata("test"));
    }

    @Test(expected = NullPointerException.class)
    public void formatNotSupplied_throwsExeption() throws IOException {
        File dest = folder.newFolder("ok");
        createServiceAndGetDescriptor(dest, null, metadata("test"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatNotValid_throwsException() throws IOException {
        File dest = folder.newFolder("ok");
        createServiceAndGetDescriptor(dest, "bogus_format", metadata("test"));
    }

    @Test(expected = NullPointerException.class)
    public void openSinkWithNoMetadata_throwsException() throws IOException {
        File dest = folder.newFolder("ok");
        createServiceAndGetDescriptor(dest, NTRIPLES, null);
    }

    @Test(expected = NullPointerException.class)
    public void openSinkWithNoName_throwsException() throws IOException {
        File dest = folder.newFolder("ok");
        createServiceAndGetDescriptor(dest, NTRIPLES, metadata(null));
    }

    @Test
    public void simpleSuccess() throws IOException {
        File dest = folder.newFolder("we_win");
        createServiceAndGetDescriptor(dest, NTRIPLES, metadata("test"));
        assertTrue(new File(dest, "test.nt").exists());
    }

    @Test
    public void checkExtensionSubstitution() throws IOException {
        File dest = folder.newFolder("we_win");
        createServiceAndGetDescriptor(dest, NTRIPLES, metadata("test.xml"));
        assertTrue(new File(dest, "test.nt").exists());
    }

    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------

    private InputMetadata metadata(String name) {
        return new InputMetadata() {
            @Override
            public String getName() {
                return name;
            }
        };
    }

    private void createServiceAndGetDescriptor(File destination, String format,
            InputMetadata metadata) throws IOException {
        service = new FileOutputService();
        service.configure(new ConfigurationNode.Builder()
                .addAttribute("destination",
                        destination == null ? null
                                : destination.getCanonicalPath())
                .addAttribute("format", format).build());
        output = service.openSink(metadata);
    }
}
