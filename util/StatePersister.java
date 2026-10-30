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
    public static void save(Account[] accounts, Path file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("Target file path cannot be null.");
        }
        if (accounts == null) {
            throw new IllegalArgumentException("Accounts array cannot be null.");
        }

        Path parent = file.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        try (OutputStream fos = Files.newOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(accounts);
        }
    }

    public static void save(java.util.Collection<Account> accounts, Path file) throws IOException {
        if (accounts == null) {
            throw new IllegalArgumentException("Accounts collection cannot be null.");
        }
        save(accounts.toArray(new Account[0]), file);
    }

    public static Account[] load(Path file) throws IOException, ClassNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("Source file path cannot be null.");
        }
        if (!Files.exists(file)) {
            throw new IOException("Source file does not exist: " + file.toAbsolutePath());
        }

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
