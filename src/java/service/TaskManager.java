package service;

import exception.*;
import model.Employee;
import model.EmployeeId;
import model.Task;
import utils.ValidationUtils;

import java.sql.*;
import java.util.*;

public class TaskManager {
    private final EmployeeService es = new EmployeeService();
    private final TaskService ts = new TaskService();
    private final AssignmentService as = new AssignmentService();

    private final Map<EmployeeId, Employee> employees;
    private final List<Task> tasksList;
    private final List<Task> unassignedTasks;
    private final Map<EmployeeId, List<Task>> assignedTasks;

    private final List<String> validUpdateTaskKeys = List.of("title", "description", "estimated_hours");

    public TaskManager() {
        this.employees = new HashMap<>();
        this.tasksList = new ArrayList<>();
        this.unassignedTasks = new ArrayList<>();
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

    public void registerTasks(List<Task> tasks) {
        if (tasks == null) throw new NullPointerException("'tasks' can not be null.");
        if (tasks.isEmpty()) throw new NullPointerException("'tasks' can not be empty.");

        List<Task> persistedTasks = ts.persistTaskInDatabase(tasks);
        tasksList.addAll(persistedTasks);
        unassignedTasks.addAll(persistedTasks);
    }

    public void assignTask(EmployeeId employeeId, int taskId) {
        ValidationUtils.validateId(taskId);
        ValidationUtils.validateId(employeeId.getId());

        Task task = findTaskById(taskId);

        if (task == null) throw new TaskNotFoundException(taskId);
        if (!employees.containsKey(employeeId)) throw new EmployeeNotFoundException(employeeId);

        as.persistTaskAssignmentInDatabase(employeeId.getId(), taskId);
        assignedTasks.get(employeeId).add(task);
        unassignedTasks.remove(task);
    }

    public void updateTask(int taskId, Map<String, Object> updates) {
        ValidationUtils.validateId(taskId);

        Task foundTask = findTaskById(taskId);

        if (foundTask == null) throw new TaskNotFoundException(taskId);

        ts.updateTaskInDatabase(taskId, updates, validUpdateTaskKeys);

        updates.forEach((k, v) -> {
            if (k.equals("title")) foundTask.setTitle((String) v);
            if (k.equals("description")) foundTask.setDescription((String) v);
            if (k.equals("estimated_hours")) foundTask.setEstimatedHours((int) v);
        });
    }

    public void removeTask(int taskId) {
        ValidationUtils.validateId(taskId);

        Task foundTask = findTaskById(taskId);

        if (foundTask == null) throw new TaskNotFoundException(taskId);

        ts.removeTaskFromDatabase(taskId);
        removeTaskFromMemory(foundTask);
    }

    private void removeTaskFromMemory(Task task) {
        this.tasksList.remove(task);
        this.unassignedTasks.remove(task);
        this.assignedTasks.values().forEach(list -> list.removeIf(t -> t.getId() == task.getId()));
    }

    public Task findTaskById(int taskId) {
        ValidationUtils.validateId(taskId);
        for (Task task: tasksList) if (task.getId() == taskId) return task;
        return null;
    }

    public Map<EmployeeId, Employee> getEmployees() {
        return employees;
    }

    public List<Task> getTasksList() {
        return tasksList;
    }

    public List<Task> getUnassignedTasks() {
        return unassignedTasks;
    }

    public Map<EmployeeId, List<Task>> getAssignedTasks() {
        return assignedTasks;
    }
}
