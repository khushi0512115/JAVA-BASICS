
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (Task task : tasks) {
                task.display();
                System.out.println("-----------------------------");
            }
        }
    }

    public void removeTask(String name) {
        Task taskToRemove = null;
        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(name)) {
                taskToRemove = task;
                break;
            }
        }
        if (taskToRemove != null) {
            tasks.remove(taskToRemove);
            System.out.println("Task \"" + name + "\" has been removed.");
        } else {
            System.out.println("Task not found.");
        }
    }
}
