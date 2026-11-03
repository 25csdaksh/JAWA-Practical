package app.test;

import app.model.Item;
import app.service.ItemService;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ItemServiceTest {

    @Test("Adding an item increases the service count")
    public void testAddItemIncreasesCount() {
        ItemService service = new ItemService();
        Assert.assertEquals(0, service.count());

        service.addItem(new Item("ITM1", "Keyboard", 1500.0));
        Assert.assertEquals(1, service.count());

        service.addItem(new Item("ITM2", "Mouse", 800.0));
        Assert.assertEquals(2, service.count());
    }

    @Test("Creating an item with negative price throws IllegalArgumentException")
    public void testInvalidPriceThrowsException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new Item("ITM3", "Faulty Item", -50.0);
        });
    }

    @Test("Adding item with duplicate ID throws IllegalArgumentException")
    public void testDuplicateIdThrowsException() {
        ItemService service = new ItemService();
        service.addItem(new Item("ITM1", "Monitor", 12000.0));

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            service.addItem(new Item("ITM1", "Duplicate Monitor", 13000.0));
        });
    }

    @Test("Saving items to disk and reloading restores state unchanged")
    public void testSaveAndReloadState() throws Exception {
        ItemService service = new ItemService();
        service.addItem(new Item("ITM1", "Laptop", 65000.0));
        service.addItem(new Item("ITM2", "Desk Lamp", 1200.0));

        Path tempFile = Paths.get("temp_items_test.dat");
        try {
            service.saveToFile(tempFile);

            ItemService reloadedService = new ItemService();
            reloadedService.loadFromFile(tempFile);

            Assert.assertEquals(service.count(), reloadedService.count());
            Item item1 = reloadedService.getItem("ITM1");
            Assert.assertNotNull(item1);
            Assert.assertEquals("Laptop", item1.getName());
            Assert.assertEquals(65000.0, item1.getPrice(), 0.001);

            Item item2 = reloadedService.getItem("ITM2");
            Assert.assertNotNull(item2);
            Assert.assertEquals("Desk Lamp", item2.getName());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    public static void runAllTests() {
        System.out.println("=============================================================");
        System.out.println(" PRACTICAL 14 - PART A: ITEM SERVICE UNIT TEST RUNNER");
        System.out.println("=============================================================");

        ItemServiceTest suite = new ItemServiceTest();
        int passed = 0;
        int failed = 0;

        Method[] methods = ItemServiceTest.class.getDeclaredMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Test.class)) {
                Test annotation = m.getAnnotation(Test.class);
                String desc = annotation.value().isEmpty() ? m.getName() : annotation.value();
                try {
                    m.invoke(suite);
                    System.out.println(" [PASS] " + m.getName() + " - " + desc);
                    passed++;
                } catch (Throwable t) {
                    Throwable cause = t.getCause() != null ? t.getCause() : t;
                    System.out.println(" [FAIL] " + m.getName() + " - " + desc + ": " + cause.getMessage());
                    failed++;
                }
            }
        }

        System.out.println("-------------------------------------------------------------");
        System.out.println(String.format("Total Tests: %d | Passed: %d | Failed: %d", passed + failed, passed, failed));
        System.out.println("=============================================================\n");
    }

    public static void main(String[] args) {
        runAllTests();
    }
}
