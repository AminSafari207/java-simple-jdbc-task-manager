package service;

import model.Employee;
import model.EmployeeId;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    public List<Employee> persistEmployeeInDatabase(List<Employee> employees) {
        if (employees == null) {
            throw new NullPointerException("employees list must not be null.");
        }

        String sqlQuery = "INSERT INTO employee (first_name, last_name, email) VALUES (?, ?, ?)";
        List<Employee> createdEmployees = new ArrayList<>();

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            for (Employee employee : employees) {
                if (employee == null) {
                    throw new NullPointerException("employee must not be null.");
                }

                String firstName = employee.getFirstName();
                String lastName = employee.getLastName();
                String email = employee.getEmail();

                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.addBatch();
            }

            ps.executeBatch();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                int i = 0;
                while (rs.next()) {
                    Employee employeeWithId = new Employee(
                            new EmployeeId(rs.getInt(1)),
                            employees.get(i).getFirstName(),
                            employees.get(i).getLastName(),
                            employees.get(i).getEmail()
                    );

                    createdEmployees.add(employeeWithId);
                    i++;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error creating employees in batch", e);
        }

        return createdEmployees;
    }
}
