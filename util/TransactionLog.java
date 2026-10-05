package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

public class TransactionLog {

    /**
     * Appends an entry line to the specified log file using Files.write with CREATE and APPEND options.
     *
     * @param file Target log file Path
     * @param line Transaction log text to append
     * @throws IOException If an I/O error occurs during write
     */
    public static void append(Path file, String line) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("Log file path cannot be null.");
        }
        if (line == null) {
            throw new IllegalArgumentException("Log line cannot be null.");
        }

        // Ensure parent directory exists
        Path parent = file.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        Files.write(
            file,
            Collections.singletonList(line),
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND
        );
    }
}
