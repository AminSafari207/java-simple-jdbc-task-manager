package model;

import utils.ValidationUtils;

public class Task {
    private int id;
    private String title;
    private String description;
    private int estimatedHours;

    public Task(String title, String description, int estimatedHours) {
        ValidationUtils.validateString("title", title, 3);
        ValidationUtils.validateString("description", description, 16);
        validateEstimatedTime(estimatedHours);

        this.title = title;
        this.description = description;
        this.estimatedHours = estimatedHours;
    }

    public Task(int id, String title, String description, int estimatedHours) {
        ValidationUtils.validateId(id);
        ValidationUtils.validateString("title", title, 3);
        ValidationUtils.validateString("description", description, 6);
        validateEstimatedTime(estimatedHours);

        this.id = id;
        this.title = title;
        this.description = description;
        this.estimatedHours = estimatedHours;
    }

    private void validateEstimatedTime(int estimatedHours) {
        if (estimatedHours < 1 || estimatedHours > 48) {
            throw new IllegalArgumentException("estimatedHours must be between 1 and 48.");
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task other = (Task) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nTitle: " + title + "\nDescription: " + description + "\nEstimated Hours: " + estimatedHours;
    }
}
