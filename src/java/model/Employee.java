package model;

import utils.ValidationUtils;

public class Employee {
    private EmployeeId id;
    private String firstName;
    private String lastName;
    private String email;

    public Employee(String firstName, String lastName, String email) {
        ValidationUtils.validateString("firstName", firstName, 3);
        ValidationUtils.validateString("lastName", lastName, 3);
        ValidationUtils.validateString("email", email);

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Employee(EmployeeId id, String firstName, String lastName, String email) {
        ValidationUtils.validateId(id.getId());
        ValidationUtils.validateString("firstName", firstName, 3);
        ValidationUtils.validateString("lastName", lastName, 3);
        ValidationUtils.validateString("email", email);

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public EmployeeId getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }
}
