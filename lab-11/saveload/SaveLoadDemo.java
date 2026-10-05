package saveload;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveLoadDemo {
    public static void main(String[] args) {
        System.out.println("=== PRACTICAL 11 PART A.1: SERIALIZATION & TRANSIENT FIELD DEMO ===");

        Path filePath = Paths.get("user_profiles.ser");

        // 1. Create an array of UserProfile objects with active session tokens
        UserProfile[] originalUsers = new UserProfile[] {
            new UserProfile(101, "daksh_soni", "daksh@example.com", "TOKEN_SECRET_98765"),
            new UserProfile(102, "alice_w", "alice@example.com", "TOKEN_SECRET_54321"),
            new UserProfile(103, "bob_m", "bob@example.com", "TOKEN_SECRET_11223")
        };

        System.out.println("\n--- [1] ORIGINAL OBJECTS BEFORE SERIALIZATION ---");
        for (UserProfile user : originalUsers) {
            System.out.println(" -> " + user);
        }

        // 2. Save array to file using ObjectOutputStream (try-with-resources)
        System.out.println("\n--- [2] SAVING OBJECT ARRAY TO: " + filePath.toAbsolutePath() + " ---");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
            oos.writeObject(originalUsers);
            System.out.println("Successfully serialized UserProfile[] (" + originalUsers.length + " objects) to file.");
        } catch (IOException e) {
            System.err.println("Serialization Error: " + e.getMessage());
            return;
        }

        // 3. Read array back into fresh objects using ObjectInputStream
        System.out.println("\n--- [3] DESERIALIZING INTO FRESH OBJECTS ---");
        UserProfile[] deserializedUsers = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            deserializedUsers = (UserProfile[]) ois.readObject();
            System.out.println("Successfully deserialized UserProfile[] from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Deserialization Error: " + e.getMessage());
            return;
        }

        // 4. Confirm data survived and transient field is not saved
        System.out.println("\n--- [4] VERIFYING SURVIVED VALUES & TRANSIENT FIELD ---");
        boolean allSurvived = true;
        boolean transientOmitted = true;

        for (int i = 0; i < deserializedUsers.length; i++) {
            UserProfile orig = originalUsers[i];
            UserProfile loaded = deserializedUsers[i];

            System.out.println(String.format("Object [%d]: %s", i, loaded));

            if (orig.getUserId() != loaded.getUserId() ||
                !orig.getUsername().equals(loaded.getUsername()) ||
                !orig.getEmail().equals(loaded.getEmail())) {
                allSurvived = false;
            }

            if (loaded.getSessionToken() != null) {
                transientOmitted = false;
            }
        }

        System.out.println("\n--- [5] VERIFICATION RESULTS ---");
        System.out.println("1. Did core state (ID, Username, Email) survive? -> " + (allSurvived ? "YES [PASSED]" : "NO [FAILED]"));
        System.out.println("2. Was transient field (sessionToken) omitted?    -> " + (transientOmitted ? "YES [PASSED - null as expected]" : "NO [FAILED]"));

        // Clean up test file
        try {
            Files.deleteIfExists(filePath);
            System.out.println("\nTemporary file '" + filePath + "' cleaned up.");
        } catch (IOException ignored) {}
    }
}
