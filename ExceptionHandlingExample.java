// Custom Exception Class
class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}

// Main Class
public class ExceptionHandlingExample {

    // Method that throws a custom exception
    public static void withdraw(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero!");
        } else {
            System.out.println("Withdrawal successful: $" + amount);
        }
    }

    public static void main(String[] args) {
        try {
            // Attempting to withdraw an invalid amount
            withdraw(-500);
        } catch (InvalidAmountException e) {
            // Handling the custom exception
            System.out.println("Exception Caught: " + e.getMessage());
        } finally {
            // Code that always executes
            System.out.println("Transaction Completed.");
        }
    }
}
