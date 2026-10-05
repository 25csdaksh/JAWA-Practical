package warehouse;

public class WarehouseDriver {
    record OrderRequest(String item, int quantity) {}

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("     PRACTICAL 8 - PART A2: WAREHOUSE STOCK      ");
        System.out.println("=================================================");

        Warehouse warehouse = new Warehouse();
        warehouse.addStock("Laptops", 10);
        warehouse.addStock("Keyboards", 25);
        warehouse.addStock("Monitors", 5);

        System.out.println("Initial Warehouse Stock:");
        System.out.println(" - Laptops   : " + warehouse.getStock("Laptops"));
        System.out.println(" - Keyboards : " + warehouse.getStock("Keyboards"));
        System.out.println(" - Monitors  : " + warehouse.getStock("Monitors"));
        System.out.println("-------------------------------------------------");

        OrderRequest[] requests = {
            new OrderRequest("Keyboards", 10),  // Success
            new OrderRequest("Laptops", -2),    // InvalidQuantityException
            new OrderRequest("Monitors", 8),    // OutOfStockException (shortfall = 3)
            new OrderRequest("Keyboards", 0),   // InvalidQuantityException
            new OrderRequest("Laptops", 5),     // Success
            new OrderRequest("Laptops", 8)      // OutOfStockException (shortfall = 3)
        };

        System.out.println("Processing batch issue requests (fault-tolerant execution):\n");

        for (int i = 0; i < requests.length; i++) {
            OrderRequest req = requests[i];
            System.out.println(String.format("Order #%d: Requesting %d units of '%s'...", (i + 1), req.quantity(), req.item()));

            try {
                warehouse.issue(req.item(), req.quantity());
            } catch (InvalidQuantityException e) {
                System.out.println(" -> [ERROR: INVALID QUANTITY] " + e.getMessage());
            } catch (OutOfStockException e) {
                System.out.println(String.format(" -> [ERROR: OUT OF STOCK] %s | Shortfall: %d units needed!",
                        e.getMessage(), e.getShortfall()));
            } catch (Exception e) {
                System.out.println(" -> [UNEXPECTED ERROR] " + e.getMessage());
            }
            System.out.println();
        }

        System.out.println("-------------------------------------------------");
        System.out.println("Final Warehouse Stock Summary:");
        System.out.println(" - Laptops   : " + warehouse.getStock("Laptops"));
        System.out.println(" - Keyboards : " + warehouse.getStock("Keyboards"));
        System.out.println(" - Monitors  : " + warehouse.getStock("Monitors"));
        System.out.println("=================================================\n");
    }
}
