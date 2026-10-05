package loganalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class LogAnalyzerDemo {
    public static void main(String[] args) {
        System.out.println("=== PRACTICAL 11 PART A.2: NIO LOG FILE ANALYZER ===");

        Path logsDirectory = Paths.get("sample_analyzer_logs");
        String searchKeyword = "ERROR";

        try {
            // 1. Create sample directory and write sample log files
            if (!Files.exists(logsDirectory)) {
                Files.createDirectories(logsDirectory);
            }

            Path appLog = logsDirectory.resolve("app_server.log");
            Path authLog = logsDirectory.resolve("auth_service.log");
            Path dbLog = logsDirectory.resolve("database.log");

            Files.write(appLog, List.of(
                "2026-10-05 10:00:01 [INFO] Server started on port 8080",
                "2026-10-05 10:01:15 [DEBUG] Loaded 45 database connections in pool",
                "2026-10-05 10:05:22 [ERROR] Connection timed out while reaching payment gateway",
                "2026-10-05 10:08:40 [INFO] Request processed successfully (HTTP 200)",
                "2026-10-05 10:12:11 [ERROR] NullPointerException in TransactionHandler.process()"
            ));

            Files.write(authLog, List.of(
                "2026-10-05 10:02:00 [INFO] Auth service initialized",
                "2026-10-05 10:03:45 [WARN] Invalid password attempt for user 'daksh'",
                "2026-10-05 10:04:10 [INFO] User 'alice' logged in successfully",
                "2026-10-05 10:11:33 [ERROR] Token verification failed: expired signature"
            ));

            Files.write(dbLog, List.of(
                "2026-10-05 09:59:50 [INFO] PostgreSQL database engine online",
                "2026-10-05 10:07:18 [INFO] Vacuum executed on table 'accounts'",
                "2026-10-05 10:14:02 [ERROR] Deadlock detected on lock key 44091",
                "2026-10-05 10:15:00 [INFO] Replication sync completed"
            ));

            System.out.println("Sample log files created in directory: " + logsDirectory.toAbsolutePath());
            System.out.println("Search Keyword: \"" + searchKeyword + "\"\n");

            long totalLinesAcrossFiles = 0;
            long totalKeywordMatches = 0;
            int fileCount = 0;

            // 2. Iterate through files using DirectoryStream and analyze with NIO Path/Files
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDirectory, "*.log")) {
                for (Path entry : stream) {
                    fileCount++;
                    // Read file attributes using Files.readAttributes
                    BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                    long fileSize = attrs.size();
                    String lastModified = attrs.lastModifiedTime().toString();

                    long fileLines = 0;
                    long fileMatches = 0;

                    System.out.println("-----------------------------------------------------------------");
                    System.out.println(String.format("File #%d: %s", fileCount, entry.getFileName()));
                    System.out.println(String.format(" -> Size: %d bytes | Last Modified: %s", fileSize, lastModified));
                    System.out.println(" -> Content Analysis:");

                    // Read line by line with BufferedReader
                    try (BufferedReader reader = Files.newBufferedReader(entry)) {
                        String line;
                        int lineNo = 1;
                        while ((line = reader.readLine()) != null) {
                            fileLines++;
                            totalLinesAcrossFiles++;
                            if (line.contains(searchKeyword)) {
                                fileMatches++;
                                totalKeywordMatches++;
                                System.out.println(String.format("    [Line %d MATCH] %s", lineNo, line));
                            }
                            lineNo++;
                        }
                    }

                    System.out.println(String.format(" -> Summary: %d lines, %d occurrences of '%s'",
                            fileLines, fileMatches, searchKeyword));
                }
            }

            // 3. Print overall aggregated statistics
            System.out.println("=================================================================");
            System.out.println("                       AGGREGATE ANALYSIS                        ");
            System.out.println("=================================================================");
            System.out.println("Total Files Analyzed : " + fileCount);
            System.out.println("Total Lines Scanned  : " + totalLinesAcrossFiles);
            System.out.println(String.format("Total '%s' Matches   : %d", searchKeyword, totalKeywordMatches));
            System.out.println("=================================================================");

            // 4. Cleanup sample directory
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDirectory)) {
                for (Path entry : stream) {
                    Files.deleteIfExists(entry);
                }
            }
            Files.deleteIfExists(logsDirectory);
            System.out.println("\nSample log files and test directory cleaned up successfully.");

        } catch (IOException e) {
            System.err.println("I/O Error during log analysis: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
