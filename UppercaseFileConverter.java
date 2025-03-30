import java.io.*;
import java.nio.file.*;

public class UppercaseFileConverter {
    public static void convertToUppercase(String filePath) {
        try {
            // Read file content
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Convert content to uppercase
            content = content.toUpperCase();

            // Write back to the same file
            Files.write(Paths.get(filePath), content.getBytes());

            System.out.println("File content converted to uppercase successfully.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String filePath = "Tokens.java"; // Taking Tokens.java as input file
        convertToUppercase(filePath);
    }
}
