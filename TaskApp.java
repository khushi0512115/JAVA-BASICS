
    

import java.util.Scanner;

public class TaskApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();

        while (true) {
            System.out.println("Task Manager Application");
            System.out.println("1. Add a task");
            System.out.println("2. View all tasks");
            System.out.println("3. Remove a task");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            switch (choice) {
                case 1: {
                    System.out.print("Enter task name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter task category (work/personal): ");
                    String category = scanner.nextLine();

                    Task newTask = new Task(name, description, category);
                    taskManager.addTask(newTask);
                    System.out.println("Task added successfully.");
                    break;
                }
                case 2: {
                    taskManager.viewTasks();
                    break;
                }
                case 3: {
                    System.out.print("Enter task name to remove: ");
                    String taskName = scanner.nextLine();
                    taskManager.removeTask(taskName);
                    break;
                }
                case 4: {
                    System.out.println("Exiting the app. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                }
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
