package service;

import utils.ValidationUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AssignmentService {
    public void persistTaskAssignmentInDatabase(int employeeId, int taskId) {
        ValidationUtils.validateId(employeeId);
        ValidationUtils.validateId(taskId);

        String sqlQuery = "insert into assigned_tasks (employee_id, task_id) values (?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sqlQuery)
        ) {
            ps.setInt(1, employeeId);
            ps.setInt(2, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error with assigning task " + taskId + " to employee " + employeeId + ": " + e);
        }
    }

}
