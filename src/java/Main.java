import model.Employee;
import model.EmployeeId;
import model.Task;
import service.EmployeeService;
import service.TaskManager;
import service.TaskService;
import utils.SqlUtils;
import utils.TaskUtils;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        SqlUtils.truncateTables("assigned_tasks", "task", "employee");

        TaskManager tm = new TaskManager();

        tm.registerEmployee(List.of(
                new Employee("Ali", "AliPour", "ali@email.com"),
                new Employee("Pedram", "PedramPour", "pedram@email.com"),
                new Employee("Bijan", "BijanPour", "bijan@email.com")
        ));

        tm.registerTasks(List.of(
                new Task("New Upload Feature", "With this feature we should be able to upload files.", 12),
                new Task("Fix Login Bug", "A login issue that happens randomly since latest version update.", 3),
                new Task("Update Phone Number Widget", "New update task details is in attached PDF file.", 16),
                new Task("Add Timezone Input", "Implement a new timezone menu input.", 1),
                new Task("New Cache Management Strategy", "For image files, we should apply 'Network First' CM strategy.", 3),
                new Task("Fix Startup Logo Duplication", "Startup logo blinks twice before loading screen.", 12),
                new Task("Fix 'Account Not Found' in AdHoc", "In AdHoc mode when a quote is not found, error page shows false message.", 6)
        ));

        Map.of(
                1, 2,
                2, 2,
                3, 1,
                4, 1,
                5, 2,
                6, 1,
                7, 3
        ).forEach((task, employeeId) -> tm.assignTask(new EmployeeId(employeeId), task));

        tm.removeTask(5);
        tm.updateTask(6, Map.of(
                "estimated_hours", 14,
                "title", "Fix Startup Logo Flicker"
        ));

        List<Task> tasksList = tm.getTasksList();
        List<Task> filteredTasks = TaskUtils.filterTasks(tasksList, task -> task.getEstimatedHours() > 10);
        List<String> summarizedTasks = TaskUtils.summarizeTasks(tasksList, task -> "'" + task.getTitle() + "': " + task.getEstimatedHours() + "h");

        System.out.println("------------------------");
        System.out.println("---- Filtered Tasks ----");
        System.out.println("------------------------");
        System.out.println();

        TaskUtils.printTasks(filteredTasks, task -> {
            System.out.println(task.toString());
            System.out.println();
            System.out.println("------------------------");
            System.out.println();
        });

        System.out.println();
        System.out.println("------------------------");
        System.out.println("---- Filtered Tasks ----");
        System.out.println("------------------------");
        System.out.println();

        summarizedTasks.forEach(summarizedTask -> {
            System.out.println(summarizedTask);
            System.out.println();
            System.out.println("------------------------");
            System.out.println();
        });
    }
}
