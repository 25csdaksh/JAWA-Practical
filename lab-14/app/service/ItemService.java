package app.service;

import app.model.Item;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item.");
        }
        for (Item existing : items) {
            if (existing.getId().equalsIgnoreCase(item.getId())) {
                throw new IllegalArgumentException("Item with ID '" + item.getId() + "' already exists.");
            }
        }
        items.add(item);
    }

    public Item getItem(String id) {
        if (id == null) return null;
        for (Item item : items) {
            if (item.getId().equalsIgnoreCase(id.trim())) {
                return item;
            }
        }
        return null;
    }

    public boolean removeItem(String id) {
        Item item = getItem(id);
        if (item != null) {
            return items.remove(item);
        }
        return false;
    }

    public ArrayList<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    public int count() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void saveToFile(Path file) throws IOException {
        Path parent = file.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            oos.writeObject(new ArrayList<>(items));
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(Path file) throws IOException, ClassNotFoundException {
        if (!Files.exists(file)) {
            throw new FileNotFoundException("File does not exist: " + file.toAbsolutePath());
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
            List<Item> loaded = (List<Item>) ois.readObject();
            items.clear();
            items.addAll(loaded);
        }
    }
}
