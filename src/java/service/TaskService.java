package service;

import exception.TaskUpdateParamException;
import model.Task;
import utils.ValidationUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskService {
    private final List<String> validUpdateKeys = List.of("title", "description", "estimated_hours");

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
            throw new RuntimeException("Error updating tasks in batch", e);
        }

        return createdTasks;
    }

    public void updateTask(Task task, Map<String, Object> updates) {
        if (updates == null || updates.isEmpty()) {
            throw new TaskUpdateParamException("'updates' parameter must not be null or empty.");
        }

        String sqlQuery = "UPDATE task SET ";
        int updateCount = 0;

        for (String key: updates.keySet()) {
            if (!validUpdateKeys.contains(key)) {
                throw new TaskUpdateParamException("'updates' parameter key is not valid: '" + key + "'");
            }

            sqlQuery += key + " = ?";
            updateCount++;

            if (updateCount < updates.size()) sqlQuery += ", ";
        }

        sqlQuery += " WHERE id = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlQuery)
        ) {
            int idIndex = 1;

            for (String key: updates.keySet()) {
                Object value = updates.get(key);

                ps.setObject(idIndex++, value);

                if (key.equals("title")) task.setTitle((String) value);
                if (key.equals("description")) task.setDescription((String) value);
                if (key.equals("estimated_hours")) task.setEstimatedHours((int) value);
            }

            ps.setInt(idIndex, task.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTask(int taskId) {
        ValidationUtils.validateId(taskId);

        String sqlQuery = "DELETE FROM task WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sqlQuery)
        ) {
            ps.setInt(1, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
