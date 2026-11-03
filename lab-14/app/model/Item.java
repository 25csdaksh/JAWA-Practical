package app.model;

import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private String name;
    private double price;

    public Item(String id, String name, double price) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Item ID cannot be null or blank.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be null or blank.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Item price cannot be negative: " + price);
        }
        this.id = id.trim();
        this.name = name.trim();
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be null or blank.");
        }
        this.name = name.trim();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Item price cannot be negative: " + price);
        }
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Item[id='%s', name='%s', price=Rs. %.2f]", id, name, price);
    }
}
