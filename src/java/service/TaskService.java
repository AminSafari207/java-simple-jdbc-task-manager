package service;

import model.Task;
import model.TaskId;
import model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    public Task createTask(Task task) {
        if (task == null) {
            throw new NullPointerException("task must not be null.");
        }

        String sqlQuery = "INSERT INTO task (title, description, estimated_hours) VALUES (?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
        ) {
            String title = task.getTitle();
            String description = task.getDescription();
            int estimatedHours = task.getEstimatedHours();

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setInt(3, estimatedHours);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Task(
                            rs.getInt(1),
                            title,
                            description,
                            estimatedHours
                    );
                } else {
                    throw new SQLException("Failed to retrieve generated task ID.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> createTask(List<Task> tasks) {
        if (tasks == null) {
            throw new NullPointerException("tasks list must not be null.");
        }

        String sqlQuery = "INSERT INTO task (title, description, estimated_hours) VALUES (?, ?, ?)";
        List<Task> createdTasks = new ArrayList<>();

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            for (Task task : tasks) {
                if (task == null) {
                    throw new NullPointerException("task must not be null.");
                }

                String title = task.getTitle();
                String description = task.getDescription();
                int estimatedHours = task.getEstimatedHours();

                ps.setString(1, title);
                ps.setString(2, description);
                ps.setInt(3, estimatedHours);
                ps.addBatch();
            }

            ps.executeBatch();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                int i = 0;
                while (rs.next()) {
                    Task taskWithId = new Task(
                            rs.getInt(1),
                            tasks.get(i).getTitle(),
                            tasks.get(i).getDescription(),
                            tasks.get(i).getEstimatedHours()
                    );

                    createdTasks.add(taskWithId);
                    i++;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error creating tasks in batch", e);
        }

        return createdTasks;
    }
}
