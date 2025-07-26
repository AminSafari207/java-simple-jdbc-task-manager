package exception;

public class TaskNotFoundException extends RuntimeException {
  public TaskNotFoundException(int taskId) {
    super("Task with id '" + taskId + "' not found.");
  }
}
