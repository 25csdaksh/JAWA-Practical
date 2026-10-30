public class MediaDriver {
    record ReturnEntry(MediaItem item, int daysLate) {}

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("    PRACTICAL 5 - PART A3: MEDIA LATE FEES       ");
        System.out.println("=================================================");

        ReturnEntry[] batch = new ReturnEntry[] {
            new ReturnEntry(new Book("BK-101", "Effective Java (Joshua Bloch)"), 1),
            new ReturnEntry(new Book("BK-102", "Clean Architecture (Robert Martin)"), 5),
            new ReturnEntry(new DVD("DVD-201", "Interstellar Collector's Edition"), 3),
            new ReturnEntry(new AudioBook("AB-301", "The Pragmatic Programmer Audiobook"), 4),
            new ReturnEntry(new Book("BK-103", "Algorithms (Sedgewick)"), 60),
            new ReturnEntry(new DVD("DVD-202", "Oppenheimer 4K UHD"), 2)
        };

        double grandTotalFee = 0.0;

        System.out.println(String.format("%-8s | %-38s | %-10s | %-12s", "Item ID", "Media Title & Type", "Days Late", "Late Fee"));
        System.out.println("----------------------------------------------------------------------------------");

        for (ReturnEntry entry : batch) {
            MediaItem item = entry.item();
            int days = entry.daysLate();
            double fee = item.computeLateFee(days);
            grandTotalFee += fee;

            System.out.println(String.format("%-8s | %-38s | %-10d | Rs. %-8.2f",
                item.getItemId(), item.toString(), days, fee));
        }

        System.out.println("----------------------------------------------------------------------------------");
        System.out.println(String.format("Batch Summary: %d items processed. Total Late Fees Due: Rs. %.2f", batch.length, grandTotalFee));
        System.out.println("=================================================\n");
    }
}
