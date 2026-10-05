package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import model.Account;

public class StatePersister {

    /**
     * Serializes an array of Account objects to the designated file path using ObjectOutputStream inside try-with-resources.
     *
     * @param accounts Array of accounts to serialize
     * @param file     Destination Path
     * @throws IOException If an I/O error occurs during serialization
     */
    public static void save(Account[] accounts, Path file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("Target file path cannot be null.");
        }
        if (accounts == null) {
            throw new IllegalArgumentException("Accounts array cannot be null.");
        }

        // Ensure parent directory exists
        Path parent = file.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        // Use ObjectOutputStream wrapped around Files.newOutputStream within try-with-resources
        try (OutputStream fos = Files.newOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(accounts);
        }
    }

    /**
     * Deserializes an array of Account objects from the specified file path using ObjectInputStream inside try-with-resources.
     *
     * @param file Source Path containing serialized accounts
     * @return Fresh array of Account objects
     * @throws IOException            If an I/O error occurs
     * @throws ClassNotFoundException If the class of a serialized object cannot be found
     */
    public static Account[] load(Path file) throws IOException, ClassNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("Source file path cannot be null.");
        }
        if (!Files.exists(file)) {
            throw new IOException("Source file does not exist: " + file.toAbsolutePath());
        }

        // Use ObjectInputStream wrapped around Files.newInputStream within try-with-resources
        try (InputStream fis = Files.newInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            if (obj instanceof Account[] accArray) {
                return accArray;
            } else {
                throw new IOException("Deserialized object is not of type Account[]: " + (obj != null ? obj.getClass().getName() : "null"));
            }
        }
    }
}
