package exception;

import model.EmployeeId;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(EmployeeId employeeId) {
        super("Task with id '" + employeeId.getId() + "' not found.");
    }
}
