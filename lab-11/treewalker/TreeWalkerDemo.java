package treewalker;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TreeWalkerDemo {
    public static void main(String[] args) {
        System.out.println("=== PRACTICAL 11 PART A.3: NIO DIRECTORY TREE WALKER & REPORT GENERATOR ===");

        Path testRoot = Paths.get("sample_sandbox_tree");
        Path reportFilePath = Paths.get("folder_tree_report.txt");

        try {
            // 1. Setup a nested folder tree with files for demonstration
            if (Files.exists(testRoot)) {
                deleteRecursively(testRoot);
            }
            Files.createDirectories(testRoot.resolve("docs/manuals"));
            Files.createDirectories(testRoot.resolve("src/main"));
            Files.createDirectories(testRoot.resolve("config"));

            Files.writeString(testRoot.resolve("README.txt"), "This is root documentation file.");
            Files.writeString(testRoot.resolve("docs/overview.md"), "# Project Overview\nSystem architecture and diagrams.");
            Files.writeString(testRoot.resolve("docs/manuals/user_guide.pdf.txt"), "User guide step-by-step instructions.");
            Files.writeString(testRoot.resolve("src/main/App.java.txt"), "public class App { public static void main(String[] args) {} }");
            Files.writeString(testRoot.resolve("config/app.properties"), "server.port=8080\nenv=production\nlogging.level=INFO");

            System.out.println("Created test directory hierarchy at: " + testRoot.toAbsolutePath());

            // 2. Walk the directory tree using Files.walkFileTree and collect metadata
            List<String> reportLines = new ArrayList<>();
            reportLines.add("==========================================================================================");
            reportLines.add("                             DIRECTORY TREE AUDIT REPORT                                  ");
            reportLines.add("==========================================================================================");
            reportLines.add(String.format("%-45s | %-12s | %-25s | %-6s", "Relative Path", "Size (bytes)", "Last Modified", "Type"));
            reportLines.add("------------------------------------------------------------------------------------------");

            final long[] totalFiles = {0};
            final long[] totalDirs = {0};
            final long[] totalBytes = {0};

            Files.walkFileTree(testRoot, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (!dir.equals(testRoot)) {
                        totalDirs[0]++;
                        Path relative = testRoot.relativize(dir);
                        reportLines.add(String.format("[DIR]  %-40s | %-12s | %-25s | DIR",
                                relative.toString(), "-", attrs.lastModifiedTime().toString()));
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    totalFiles[0]++;
                    totalBytes[0] += attrs.size();
                    Path relative = testRoot.relativize(file);
                    reportLines.add(String.format("[FILE] %-40s | %-12d | %-25s | FILE",
                            relative.toString(), attrs.size(), attrs.lastModifiedTime().toString()));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    reportLines.add(String.format("[ERR]  %-40s | FAILED: %s", testRoot.relativize(file), exc.getMessage()));
                    return FileVisitResult.CONTINUE;
                }
            });

            reportLines.add("==========================================================================================");
            reportLines.add(String.format("Summary: Total Directories = %d, Total Files = %d, Total Cumulative Size = %d bytes",
                    totalDirs[0], totalFiles[0], totalBytes[0]));
            reportLines.add("==========================================================================================");

            // 3. Write report to output file
            try (BufferedWriter writer = Files.newBufferedWriter(reportFilePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                for (String line : reportLines) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            System.out.println("Tree walk report successfully written to: " + reportFilePath.toAbsolutePath());
            System.out.println("\n--- [REPORT PREVIEW] ---");
            for (String line : reportLines) {
                System.out.println(line);
            }

            // 4. Clean up test tree and report file
            deleteRecursively(testRoot);
            Files.deleteIfExists(reportFilePath);
            System.out.println("\nCleaned up sample tree folder and report file successfully.");

        } catch (IOException e) {
            System.err.println("Error during TreeWalker execution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void deleteRecursively(Path root) throws IOException {
        if (!Files.exists(root)) return;
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
