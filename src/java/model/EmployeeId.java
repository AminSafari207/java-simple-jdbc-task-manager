package model;

import utils.ValidationUtils;

public class EmployeeId {
    private final int id;

    public EmployeeId(int id) {
        ValidationUtils.validateId(id);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EmployeeId other = (EmployeeId) obj;
        return id == ((EmployeeId) obj).getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
