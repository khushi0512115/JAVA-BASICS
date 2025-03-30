public class Task {
    private String name;
    private String description;
    private String category;

    public Task(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public void display() {
        System.out.println("Task Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Category: " + category);
    }
}
