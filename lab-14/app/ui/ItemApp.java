package app.ui;

import app.model.Item;
import app.service.ItemService;
import app.test.ItemServiceTest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ItemApp {
    private final ItemService itemService = new ItemService();
    private final Path storagePath = Paths.get("items_data.dat");

    public void start() {
        System.out.println("=============================================================");
        System.out.println(" PRACTICAL 14 - PART A: TINY LAYERED APPLICATION (ITEM APP)");
        System.out.println(" Layered Architecture: UI -> Service -> Model");
        System.out.println("=============================================================");

        itemService.addItem(new Item("ITM101", "Mechanical Keyboard", 3500.0));
        itemService.addItem(new Item("ITM102", "Wireless Mouse", 1200.0));
        itemService.addItem(new Item("ITM103", "USB-C Dock", 4500.0));

        System.out.println("Pre-loaded " + itemService.count() + " items into ItemService.");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- ITEM APP MENU ---");
            System.out.println("1. List All Items");
            System.out.println("2. Add New Item");
            System.out.println("3. Find Item by ID");
            System.out.println("4. Delete Item");
            System.out.println("5. Save Items to Disk");
            System.out.println("6. Load Items from Disk");
            System.out.println("7. Run Unit Tests (@Test)");
            System.out.println("8. Exit");
            System.out.print("Enter choice (1-8): ");

            if (!scanner.hasNextInt()) {
                break;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("\nAll Registered Items (" + itemService.count() + "):");
                    for (Item item : itemService.getAllItems()) {
                        System.out.println(" -> " + item);
                    }
                }
                case 2 -> {
                    System.out.print("Enter Item ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Item Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Price (Rs.): ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    try {
                        itemService.addItem(new Item(id, name, price));
                        System.out.println("[SUCCESS] Item added successfully.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("[ERROR] Failed to add item: " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.print("Enter Item ID: ");
                    String id = scanner.nextLine();
                    Item item = itemService.getItem(id);
                    if (item != null) {
                        System.out.println("[FOUND] " + item);
                    } else {
                        System.out.println("[NOT FOUND] Item '" + id + "' not found.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter Item ID to delete: ");
                    String id = scanner.nextLine();
                    boolean removed = itemService.removeItem(id);
                    if (removed) {
                        System.out.println("[SUCCESS] Item deleted.");
                    } else {
                        System.out.println("[NOT FOUND] Item not found.");
                    }
                }
                case 5 -> {
                    try {
                        itemService.saveToFile(storagePath);
                        System.out.println("[SUCCESS] Saved " + itemService.count() + " items to " + storagePath.toAbsolutePath());
                    } catch (Exception e) {
                        System.out.println("[ERROR] Save failed: " + e.getMessage());
                    }
                }
                case 6 -> {
                    try {
                        itemService.loadFromFile(storagePath);
                        System.out.println("[SUCCESS] Loaded " + itemService.count() + " items from " + storagePath.toAbsolutePath());
                    } catch (Exception e) {
                        System.out.println("[ERROR] Load failed: " + e.getMessage());
                    }
                }
                case 7 -> {
                    ItemServiceTest.runAllTests();
                }
                case 8 -> {
                    System.out.println("Exiting ItemApp. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please choose 1-8.");
            }
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("--test")) {
            ItemServiceTest.runAllTests();
            return;
        }
        new ItemApp().start();
    }
}
