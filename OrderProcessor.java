
import java.util.Locale;

public class OrderProcessor {

    public static double processOrder(
            String customer,
            String customerType,
            int quantity,
            double unitPrice,
            double distance,
            boolean express,
            boolean printResult) {

        // 1. Validate inputs
        if (customer == null || customer.isBlank()) {
            throw new IllegalArgumentException("Invalid customer");
        }
        if (quantity <= 0 || unitPrice < 0 || distance < 0) {
            throw new IllegalArgumentException("Invalid order");
        }

        // 2. Calculate subtotal
        double subtotal = 0;
        for (int i = 0; i < quantity; i++) {
            subtotal += unitPrice;
        }

        // 3. Calculate customer discount
        double discountRate = 0;
        if ("PREMIUM".equalsIgnoreCase(customerType)) {
            discountRate = 0.10;
        } else if ("GOLD".equalsIgnoreCase(customerType)) {
            discountRate = 0.15;
        } else if ("STANDARD".equalsIgnoreCase(customerType)) {
            discountRate = 0.02;
        }

        // 4. Additional bulk discount
        if (quantity >= 100) {
            discountRate += 0.08;
        } else if (quantity >= 50) {
            discountRate += 0.05;
        } else if (quantity >= 10) {
            discountRate += 0.02;
        }

        // 5. Apply discount
        if (discountRate > 0.30) {
            discountRate = 0.30;
        }
        double discount = subtotal * discountRate;
        double discountedSubtotal = subtotal - discount;

        // 6. Calculate shipping
        double shipping = 5.0;
        if (distance > 500) {
            shipping += 30.0;
        } else if (distance > 200) {
            shipping += 15.0;
        } else if (distance > 50) {
            shipping += 5.0;
        }

        // 7. Apply express delivery charge
        if (express) {
            shipping += 15.0;
        }

        // 8. Free shipping eligibility
        if (discountedSubtotal >= 1000 && !express) {
            shipping = 0;
        }

        // 9. Calculate tax
        double taxableAmount = discountedSubtotal;
        double taxRate = 0.20;
        double tax = taxableAmount * taxRate;

        // 10. Calculate final total
        double finalTotal = discountedSubtotal + shipping + tax;

        // 11. Produce output
        if (printResult) {
            System.out.println("Customer: " + customer);
            System.out.printf(Locale.US,
                "Subtotal: EUR %.2f%n", subtotal);
            System.out.printf(Locale.US,
                "Discount: EUR %.2f%n", discount);
            System.out.printf(Locale.US,
                "Shipping: EUR %.2f%n", shipping);
            System.out.printf(Locale.US,
                "Tax: EUR %.2f%n", tax);
            System.out.printf(Locale.US,
                "Final Total: EUR %.2f%n", finalTotal);
        }

        return finalTotal;
    }

    public static void main(String[] args) {
        processOrder(
            "Alice", "PREMIUM", 12,
            49.99, 120, true, true
        );
    }
}
