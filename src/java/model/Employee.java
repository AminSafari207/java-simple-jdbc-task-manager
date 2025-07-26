package model;

import utils.ValidationUtils;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;

    public Employee(String firstName, String lastName) {
        ValidationUtils.validateString("firstName", firstName);
        ValidationUtils.validateString("lastName", lastName);

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(int id, String firstName, String lastName) {
        ValidationUtils.validateId(id);
        ValidationUtils.validateString("firstName", firstName);
        ValidationUtils.validateString("lastName", lastName);

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
