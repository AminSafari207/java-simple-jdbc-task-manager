package utils;

import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class TaskUtils {
    public static List<Task> filterTasks(List<Task> tasks, Predicate<Task> predicate) {
        List<Task> filteredTasks = new ArrayList<>();

        tasks.forEach((task) -> {
            if (predicate.test(task)) filteredTasks.add(task);
        });

        return filteredTasks;
    }

    public static List<String> summarizeTasks(List<Task> tasks, Function<Task, String> function) {
        List<String> summaries = new ArrayList<>();
        tasks.forEach(task -> summaries.add(function.apply(task)));
        return summaries;
    }

    public static void printTasks(List<Task> tasks, Consumer<Task> consumer) {
        tasks.forEach(task -> consumer.accept(task));
    }
}
