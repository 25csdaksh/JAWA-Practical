package visitors;

import java.util.*;

public class UniqueVisitorsDemo {
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println(" PRACTICAL 12 — PART A.2: UNIQUE VISITORS (HASHSET & LINKEDHASHSET)");
        System.out.println("=============================================================");

        List<String> rawLogins = List.of(
            "daksh_s",
            "alice_wonder",
            "bob_builder",
            "daksh_s",
            "charlie_brown",
            "alice_wonder",
            "david_copper",
            "bob_builder",
            "eve_online",
            "daksh_s",
            "frank_castle"
        );

        System.out.println("Raw Incoming Login Stream (with duplicates):");
        for (int i = 0; i < rawLogins.size(); i++) {
            System.out.println(String.format(" [%2d] %s", i + 1, rawLogins.get(i)));
        }
        System.out.println("Total Login Events Recorded: " + rawLogins.size());

        Set<String> hashSet = new HashSet<>(rawLogins);
        System.out.println("\n--- [1. HashSet Evaluation (Unordered O(1) Membership)] ---");
        System.out.println("Unique User Count (via HashSet.size()): " + hashSet.size());
        System.out.println("HashSet Elements (order is unpredictable due to bucket hashing):");
        System.out.println("  " + hashSet);

        Set<String> linkedHashSet = new LinkedHashSet<>(rawLogins);
        System.out.println("\n--- [2. LinkedHashSet Evaluation (Doubly-Linked First-Seen Order)] ---");
        System.out.println("Unique User Count (via LinkedHashSet.size()): " + linkedHashSet.size());
        System.out.println("LinkedHashSet Elements (guarantees exact first-seen sequence):");
        System.out.println("  " + linkedHashSet);

        System.out.println("\n--- [3. First Three Distinct Visitors] ---");
        List<String> firstSeenList = new ArrayList<>(linkedHashSet);
        int countToPrint = Math.min(3, firstSeenList.size());
        for (int i = 0; i < countToPrint; i++) {
            System.out.println(String.format(" %d. %s", i + 1, firstSeenList.get(i)));
        }

        System.out.println("\nVerification Summary:");
        System.out.println(" - Total Raw Logins   : " + rawLogins.size());
        System.out.println(" - Unique Visitor Count: " + linkedHashSet.size());
        System.out.println(" - First 3 Distinct   : " + firstSeenList.subList(0, countToPrint));
        System.out.println("=============================================================\n");
    }
}
