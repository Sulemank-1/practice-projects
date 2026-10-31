import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class TaskTracker {
    public static void main(String[] args) {
        getArguments(args);
    }

    private ArrayList<Task> tasks;


    public TaskTracker(){
        tasks = new ArrayList<>();
    }


    public static void getArguments(String[] args) {
        if (args.length == 0){
            System.out.println("No commands found");
            return;
        }
        TaskTracker tracker = new TaskTracker();
        tracker.loadFromFile();
        String description;
        int id;
        try {
            switch (args[0]){
                case "add":
                    if (args.length < 2)
                        System.out.println("Incomplete Command");
                    else {
                        description = args[1];
                        tracker.addTask(description);
                        tracker.saveToFile();
                    }
                    break;
                case "update":
                    if (args.length < 3)
                        System.out.println("Incomplete Command");
                    else {
                        id = Integer.parseInt(args[1]);
                        description = args[2];
                        tracker.updateTask(id, description);
                    }
                    break;
                case "list":
                    if (!tracker.tasks.isEmpty()) {
                        tracker.listTasks();
                    }
                    else
                        System.out.println("No tasks found");
                break;
                case "delete":
                    if (args.length < 2)
                        System.out.println("Incomplete Command");
                    else{
                        id = Integer.parseInt(args[1]);
                        tracker.deleteTask(id);
                    }
                break;
                case "mark-in-progress":
                    if (args.length < 2)
                        System.out.println("Incomplete Command");
                    else{
                        id = Integer.parseInt(args[1]);
                        tracker.updateTaskStatus(id, Status.IN_PROGRESS);
                    }
                break;
                case "mark-done":
                    if (args.length < 2)
                        System.out.println("Incomplete Command");
                    else{
                        id = Integer.parseInt(args[1]);
                        tracker.updateTaskStatus(id, Status.DONE);
                    }
                break;
                default:
                    System.out.println("Invalid command");
            }
        }catch (NumberFormatException ex){
            System.out.println("Enter an integer for id");
        }catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("Incomplete command");
        }

    }

    //Add a task
    public void addTask(String description){
        if (tasks.add(new Task(description)))
            System.out.println("Task added.");
    }

    //Update task
    public boolean updateTask(int id, String description){
        for (Task task: tasks){
            if (task.getId() == id){
                task.setDescription(description);
                System.out.println("Task updated");
                return true;
            }
        }
        System.out.println("Task id not found");
        return false;
    }

    //Update task status
    public boolean updateTaskStatus(int id, Status status){
        for (Task task: tasks){
            if (task.getId() == id){
                task.setStatus(status);
                System.out.println("Task status updated");
                return true;
            }
        }
        System.out.println("Task id not found");
        return false;
    }

    //Delete task
    public boolean deleteTask(int id){
        for (Task task: tasks){
            if (task.getId() == id){
                if (tasks.remove(task))
                    System.out.println("Task Deleted");
                return true;
            }
        }
        System.out.println("Task id not found");
        return false;
    }

    //List all tasks
    public void listTasks(){
        for (Task task: tasks){
            System.out.println(task);
        }
    }

    //Save tasks to file
    public void saveToFile(){
        try {
            List<String> taskList = new ArrayList<>();
            for (Task task: tasks){
                taskList.add(
                           """
                           {
                            "id": %d,
                            "description": "%s",
                            "status": "%s"
                            }
                        """.formatted(task.getId(), task.getDescription(), task.getStatus())
                );
            }
            String taskString = String.join(",\n", taskList);
            Path path = Paths.get("tasks.json");
            Files.writeString(path, "[\n" + taskString + "]");

        }catch (IOException ex){
            System.out.println(ex);
        }
    }

    //Load files from task
    public void loadFromFile(){
        Path path = Paths.get("tasks.json");
        try {
            String taskString = "";
            if (Files.exists(path))
                taskString = Files.readString(path);

            String regex = "\"id\"\\s*:\\s*(\\d+),\\s*\"description\"\\s*:\\s*\"([^\"]*)\",\\s*\"status\"\\s*:\\s*\"([^\"]*)\"";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(taskString);
            while (matcher.find()) {
                int id = Integer.parseInt(matcher.group(1));
                String description = matcher.group(2);
                Status status = Status.valueOf(matcher.group(3));
                tasks.add(new Task(id, description, status));
            }
        } catch (IOException ex) {
            System.out.println(ex);;
        }
    }
}
