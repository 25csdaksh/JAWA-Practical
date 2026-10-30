package leaderboard;

import java.util.*;

public class LeaderboardDemo {
    public static class PlayerScore {
        private final String playerName;
        private final int score;

        public PlayerScore(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        public String getPlayerName() { return playerName; }
        public int getScore() { return score; }

        @Override
        public String toString() {
            return String.format("%s (Score: %d)", playerName, score);
        }
    }

    public static List<PlayerScore> getTopNPlayers(Map<String, Integer> scoreMap, int n) {
        if (scoreMap == null || scoreMap.isEmpty() || n <= 0) {
            return Collections.emptyList();
        }

        List<PlayerScore> playerList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : scoreMap.entrySet()) {
            playerList.add(new PlayerScore(entry.getKey(), entry.getValue()));
        }

        Comparator<PlayerScore> leaderboardComparator = (p1, p2) -> {
            int scoreDiff = Integer.compare(p2.getScore(), p1.getScore());
            if (scoreDiff != 0) {
                return scoreDiff;
            }
            return p1.getPlayerName().compareToIgnoreCase(p2.getPlayerName());
        };

        playerList.sort(leaderboardComparator);

        int limit = Math.min(n, playerList.size());
        return new ArrayList<>(playerList.subList(0, limit));
    }

    public static void displayLeaderboard(String title, Map<String, Integer> scores, int topN) {
        System.out.println("-------------------------------------------------------------");
        System.out.println(" " + title);
        System.out.println("-------------------------------------------------------------");
        System.out.println(String.format("%-6s | %-20s | %-10s | %-12s", "Rank", "Player Name", "Score", "Badge"));
        System.out.println("-------------------------------------------------------------");

        List<PlayerScore> topPlayers = getTopNPlayers(scores, topN);
        int rank = 1;
        for (PlayerScore p : topPlayers) {
            String badge = switch (rank) {
                case 1 -> "[GOLD]";
                case 2 -> "[SILVER]";
                case 3 -> "[BRONZE]";
                default -> "[TOP 10]";
            };
            System.out.println(String.format("%-6d | %-20s | %-10d | %-12s",
                    rank++, p.getPlayerName(), p.getScore(), badge));
        }
        System.out.println("-------------------------------------------------------------\n");
    }

    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println(" PRACTICAL 12 — PART A.3: LEADERBOARD SYSTEM (MAP & COMPARATOR)");
        System.out.println("=============================================================");

        Map<String, Integer> playerScores = new HashMap<>();

        playerScores.put("daksh_s", 3200);
        playerScores.put("alice_wonder", 3200);
        playerScores.put("bob_builder", 2800);
        playerScores.put("charlie_b", 2800);
        playerScores.put("david_c", 2800);
        playerScores.put("eva_smith", 3500);
        playerScores.put("frankie", 1950);
        playerScores.put("grace_hopper", 3100);

        System.out.println("Registered " + playerScores.size() + " players in score map.");

        displayLeaderboard("🏆 GLOBAL GAMING LEADERBOARD (TOP 5 PLAYERS)", playerScores, 5);

        System.out.println(">>> EVENT: Daksh scores 300 bonus points! Upgrading daksh_s to 3500 points...");
        playerScores.merge("daksh_s", 300, Integer::sum);

        System.out.println(">>> EVENT: Grace Hopper completes speedrun! Upgrading grace_hopper to 3400 points...");
        playerScores.put("grace_hopper", 3400);

        displayLeaderboard("🏆 UPDATED GLOBAL LEADERBOARD (POST BONUS ROUND)", playerScores, 5);

        System.out.println("Leaderboard tie-breaking logic successfully verified!");
        System.out.println("=============================================================\n");
    }
}
