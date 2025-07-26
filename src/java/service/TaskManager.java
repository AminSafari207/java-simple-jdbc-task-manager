package service;

import exception.EmployeeIdException;
import exception.EmployeesListException;
import exception.TasksMapException;
import model.Employee;
import model.EmployeeId;
import model.Task;

import java.sql.*;
import java.util.*;

public class TaskManager {
    private final EmployeeService es = new EmployeeService();
    private final TaskService ts = new TaskService();

    private final List<Task> unassignedTasks;
    private final Map<EmployeeId, Employee> employees;
    private final Map<EmployeeId, List<Task>> assignedTasks;

    public TaskManager() {
        this.unassignedTasks = new ArrayList<>();
        this.employees = new HashMap<>();
        this.assignedTasks = new HashMap<>();
    }

    public void registerEmployee(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            throw new EmployeesListException("employees list must not be null or empty");
        }

        List<Employee> employeesWithId = es.persistEmployeeInDatabase(employees);

        for (Employee employee: employeesWithId) {
            if (employee == null) {
                throw new NullPointerException("employee must not be null.");
            }

            this.employees.put(employee.getId(), employee);
            this.assignedTasks.put(employee.getId(), new ArrayList<>());
        }
    }

    public void registerTask(Map<EmployeeId, Task> tasksMap) {
        if (tasksMap == null || tasksMap.isEmpty()) throw new TasksMapException("tasksMap must not be null or empty.");

        ts.persistTaskInDatabase((List<Task>) tasksMap.values());

        tasksMap.forEach((employeeId, task) -> {
            if (employeeId == null)  throw new NullPointerException("employeeId must not be null.";
            if (task == null) throw new NullPointerException("task must not be null.");

            int id = employeeId.getId();

            if (id == -1) {
                this.unassignedTasks.add(task);
                return;
            }

            this.assignedTasks.get(employeeId).add(task);
        });
    }

}
