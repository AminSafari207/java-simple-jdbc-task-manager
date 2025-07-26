package service;

import exception.EmployeesListException;
import model.Employee;
import model.EmployeeId;
import model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        }
    }

}
