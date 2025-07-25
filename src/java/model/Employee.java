package model;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;

    public Employee(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new NullPointerException("firstName or lastName can not be null.'");
        }

        if (firstName.trim().isEmpty() || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("firstName or lastName can not be empty strings.");
        }

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
}
