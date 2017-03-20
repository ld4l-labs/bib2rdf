/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.io.InputService.InputMetadata;

/**
 * OutputService that writes to files in a directory.
 */
public class FileOutputService implements OutputService {
    /**
     * A list of the supported RDF formats, each with the information needed to
     * process it.
     */
    protected enum Format {
        NTRIPLES("nt", "N-TRIPLE");

        private static Format fromString(String string) {
            for (Format f : values()) {
                if (f.getLanguage().equals(string)) {
                    return f;
                }
            }
            throw new IllegalArgumentException(
                    "Not a valid input format: '" + string + "'");
        }

        private final String extension; // Append to filename with period
        private final String language;  // As required by Model.write()

        private Format(String extension, String language) {
            this.extension = extension;
            this.language = language;
        }

        public String getExtension() {
            return extension;
        }

        public String getLanguage() {
            return language;
        }
    }

    private final File destination;
    private final Format format;

    public FileOutputService(Configuration config) throws IOException {
        this.destination = new File(
                Objects.requireNonNull(config.getOutputDestination(),
                        "You must provide an output destination."));

        if (!destination.exists()) {
            throw new FileNotFoundException(
                    "Output destination doesn't exist: '" + destination + "'");
        }

        if (!destination.isDirectory()) {
            throw new IllegalArgumentException(
                    "Output destination must be a directory: '" + destination
                            + "'");
        }

        if (!destination.canWrite()) {
            throw new IOException(
                    "Can't write output destination: " + destination);
        }

        this.format = Format.fromString(Objects.requireNonNull(
                config.getOutputFormat(), "Format may not be null"));

    }

    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.io.OutputService#openSink(org.ld4l.bib2lod.io.InputService.InputMetadata)
     */
    @Override
    public OutputDescriptor openSink(InputMetadata metadata)
            throws IOException {
        Objects.requireNonNull(metadata, "metadata may not be null");
        String name = Objects.requireNonNull(metadata.getName(),
                "metadata must include a name.");
        return new FileOutputDescriptor(format, destination, name);
    }

}
