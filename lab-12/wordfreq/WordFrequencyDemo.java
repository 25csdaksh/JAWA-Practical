package wordfreq;

import java.util.*;

public class WordFrequencyDemo {
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println(" PRACTICAL 12 — PART A.1: WORD FREQUENCY (HASHMAP & COMPARATOR)");
        System.out.println("=============================================================");

        String paragraph = """
                Java collections framework provides architecture to store and manipulate a group of objects.
                Java collections can perform all the operations that you perform on a data such as searching,
                sorting, insertion, manipulation, and deletion. Java collections framework includes interfaces
                like List, Set, Queue, Deque and classes like ArrayList, Vector, LinkedList, PriorityQueue,
                HashSet, LinkedHashSet, and TreeSet. In Java collections, HashMap provides fast lookups and
                efficient key-value storage. Java collections make programming in Java enjoyable and productive.
                """;

        System.out.println("Input Paragraph:\n" + paragraph.trim() + "\n");

        String[] words = paragraph.toLowerCase().split("[^a-zA-Z0-9]+");

        Map<String, Integer> freqMap = new HashMap<>();
        int totalWordCount = 0;
        for (String word : words) {
            if (word.isBlank()) continue;
            totalWordCount++;
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }

        System.out.println(String.format("Total Words Counted: %d | Unique Words in HashMap: %d\n",
                totalWordCount, freqMap.size()));

        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(freqMap.entrySet());

        Comparator<Map.Entry<String, Integer>> byFrequencyDescending =
                (e1, e2) -> {
                    int freqCompare = e2.getValue().compareTo(e1.getValue());
                    if (freqCompare != 0) {
                        return freqCompare;
                    }
                    return e1.getKey().compareTo(e2.getKey());
                };

        entryList.sort(byFrequencyDescending);

        System.out.println("-------------------------------------------------------------");
        System.out.println(String.format("%-6s | %-20s | %-10s | %-10s", "Rank", "Word", "Frequency", "Share (%)"));
        System.out.println("-------------------------------------------------------------");

        int rank = 1;
        for (Map.Entry<String, Integer> entry : entryList) {
            double share = (entry.getValue() * 100.0) / totalWordCount;
            System.out.println(String.format("%-6d | %-20s | %-10d | %6.2f%%",
                    rank++, entry.getKey(), entry.getValue(), share));
        }
        System.out.println("-------------------------------------------------------------");
        System.out.println("Word frequency sorting completed successfully using Comparator!\n");
    }
}
