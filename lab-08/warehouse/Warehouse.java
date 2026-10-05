package warehouse;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private final Map<String, Integer> inventory = new HashMap<>();

    public void addStock(String item, int qty) {
        inventory.put(item.toLowerCase(), inventory.getOrDefault(item.toLowerCase(), 0) + qty);
    }

    public int getStock(String item) {
        return inventory.getOrDefault(item.toLowerCase(), 0);
    }

    public synchronized void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0) {
            throw new InvalidQuantityException("Requested quantity must be positive. Provided: " + qty);
        }

        String key = item.toLowerCase();
        int currentStock = inventory.getOrDefault(key, 0);

        if (currentStock < qty) {
            int shortfall = qty - currentStock;
            throw new OutOfStockException(
                String.format("Insufficient stock for item '%s'. Available: %d, Requested: %d", item, currentStock, qty),
                shortfall
            );
        }

        inventory.put(key, currentStock - qty);
        System.out.println(String.format("[SUCCESS] Issued %d units of '%s'. Remaining stock: %d", 
                qty, item, inventory.get(key)));
    }
}
