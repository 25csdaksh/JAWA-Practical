package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    /**
     * Walks the logs folder using Files.newDirectoryStream, reads log files line by line,
     * calculates total deposits, withdrawals and net change, inspects file attributes,
     * and outputs a summary report to reportFile.
     *
     * Rules:
     * - Skips files that do not end with .log
     * - Skips files that are empty (size == 0 bytes)
     *
     * @param logsFolder Directory containing transaction log files
     * @param reportFile Destination report file Path
     * @return The formatted summary report text
     * @throws IOException If an I/O error occurs
     */
    public static String generateReport(Path logsFolder, Path reportFile) throws IOException {
        if (logsFolder == null || reportFile == null) {
            throw new IllegalArgumentException("Logs folder and report file paths must not be null.");
        }

        if (!Files.exists(logsFolder)) {
            Files.createDirectories(logsFolder);
        }

        long totalDeposits = 0;
        long totalWithdrawals = 0;
        long totalTransactions = 0;
        int validLogFilesCount = 0;
        int skippedFilesCount = 0;

        List<String> fileSummaries = new ArrayList<>();

        // Walk directory entries using Files.newDirectoryStream
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsFolder)) {
            for (Path entry : stream) {
                // Skip subdirectories
                if (Files.isDirectory(entry)) {
                    skippedFilesCount++;
                    continue;
                }

                String filename = entry.getFileName().toString();

                // Supplementary Rule 1: Skip files that do not end with .log
                if (!filename.toLowerCase().endsWith(".log")) {
                    skippedFilesCount++;
                    continue;
                }

                // Read attributes using Files.readAttributes
                BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                long fileSize = attrs.size();

                // Supplementary Rule 2: Skip empty files
                if (fileSize == 0) {
                    skippedFilesCount++;
                    continue;
                }

                validLogFilesCount++;
                long fileDeposits = 0;
                long fileWithdrawals = 0;
                int fileTxCount = 0;

                // Read line by line with BufferedReader in try-with-resources
                try (BufferedReader reader = Files.newBufferedReader(entry, StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (line.isEmpty() || line.startsWith("#")) {
                            continue;
                        }

                        // Parse log tokens (e.g. "DEPOSIT AC0001 500" or "[2026-10-05] DEPOSIT AC0001 500")
                        String[] tokens = line.split("\\s+");
                        int typeIdx = -1;
                        for (int i = 0; i < tokens.length; i++) {
                            if (tokens[i].equalsIgnoreCase("DEPOSIT") || tokens[i].equalsIgnoreCase("WITHDRAW")) {
                                typeIdx = i;
                                break;
                            }
                        }

                        if (typeIdx != -1 && typeIdx + 2 < tokens.length) {
                            String type = tokens[typeIdx].toUpperCase();
                            String amountStr = tokens[typeIdx + 2];
                            try {
                                long amount = Long.parseLong(amountStr);
                                if (type.equals("DEPOSIT")) {
                                    fileDeposits += amount;
                                    fileTxCount++;
                                } else if (type.equals("WITHDRAW")) {
                                    fileWithdrawals += amount;
                                    fileTxCount++;
                                }
                            } catch (NumberFormatException ignored) {}
                        } else if (tokens.length >= 3) {
                            // Direct format: "DEPOSIT AC0001 500"
                            String type = tokens[0].toUpperCase();
                            try {
                                long amount = Long.parseLong(tokens[2]);
                                if (type.equals("DEPOSIT")) {
                                    fileDeposits += amount;
                                    fileTxCount++;
                                } else if (type.equals("WITHDRAW")) {
                                    fileWithdrawals += amount;
                                    fileTxCount++;
                                }
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                }

                totalDeposits += fileDeposits;
                totalWithdrawals += fileWithdrawals;
                totalTransactions += fileTxCount;

                fileSummaries.add(String.format("  * %-22s | Size: %6d bytes | Last-Modified: %-25s | Tx: %3d | +Rs. %-8d | -Rs. %-8d",
                        filename, fileSize, attrs.lastModifiedTime().toString(), fileTxCount, fileDeposits, fileWithdrawals));
            }
        }

        long netChange = totalDeposits - totalWithdrawals;
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        StringBuilder sb = new StringBuilder();
        sb.append("====================================================================================================\n");
        sb.append("                               MINIBANK END-OF-DAY AUDIT & RECONCILIATION REPORT                    \n");
        sb.append("====================================================================================================\n");
        sb.append(String.format("Generated At      : %s\n", timestamp));
        sb.append(String.format("Logs Directory    : %s\n", logsFolder.toAbsolutePath()));
        sb.append(String.format("Log Files Scanned : %d (Skipped/Empty/Non-.log files: %d)\n", validLogFilesCount, skippedFilesCount));
        sb.append("----------------------------------------------------------------------------------------------------\n");
        sb.append("FILE BREAKDOWN:\n");
        if (fileSummaries.isEmpty()) {
            sb.append("  (No active .log transaction files found in directory)\n");
        } else {
            for (String summary : fileSummaries) {
                sb.append(summary).append("\n");
            }
        }
        sb.append("----------------------------------------------------------------------------------------------------\n");
        sb.append("FINANCIAL RECONCILIATION SUMMARY:\n");
        sb.append(String.format("  Total Transactions Processed : %d\n", totalTransactions));
        sb.append(String.format("  Total Deposits               : Rs. %d\n", totalDeposits));
        sb.append(String.format("  Total Withdrawals            : Rs. %d\n", totalWithdrawals));
        sb.append(String.format("  Net Balance Change           : Rs. %+d (%s)\n", netChange, (netChange >= 0 ? "SURPLUS" : "DEFICIT")));
        sb.append("====================================================================================================\n");

        String reportContent = sb.toString();

        // Ensure parent directory for report file exists
        Path reportParent = reportFile.getParent();
        if (reportParent != null && !Files.exists(reportParent)) {
            Files.createDirectories(reportParent);
        }

        // Write report to destination file
        try (BufferedWriter writer = Files.newBufferedWriter(reportFile, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(reportContent);
        }

        return reportContent;
    }
}
