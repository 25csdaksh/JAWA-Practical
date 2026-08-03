import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        String[] logs = {
            "10:05 alice Hello there",
            "10:06 bob How is the weather today?",
            "10:07 charlie hello everyone!",
            "10:08 malformed_line_no_message",
            "10:09 dave I'm doing good, hello alice."
        };

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== CHAT LOG FILTER TEST ===");
        System.out.println("Available logs:");
        for (String log : logs) {
            System.out.println("  " + log);
        }

        System.out.print("\nEnter keyword to search: ");
        String keyword = scanner.nextLine().trim();

        int matchCount = 0;
        StringBuilder report = new StringBuilder();

        for (String line : logs) {
            String[] parts = line.split(" ", 3);
            if (parts.length < 3) {
                // System.out.println("[INFO] Skipping malformed line: " + line);
                continue;
            }

            String time = parts[0];
            String user = parts[1];
            String message = parts[2];

            if (ChatFilter.containsKeyword(message, keyword)) {
                matchCount++;
                report.append(time).append(" ").append(user).append(": ").append(message).append("\n");
            }
        }

        System.out.println("\nMatches: " + matchCount);
        if (matchCount > 0) {
            System.out.print(report.toString());
        } else {
            System.out.println("No matching logs found.");
        }
        
        scanner.close();
    }
}
