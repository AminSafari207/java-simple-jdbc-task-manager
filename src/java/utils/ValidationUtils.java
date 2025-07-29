package utils;

import java.util.List;

public class ValidationUtils {
    public static void validateId(int id) {
        if (id < 0) throw new IllegalArgumentException("id must 0 or positive.");
    }

    public static void validateString(String logName, String str) {
        if (str == null) throw new NullPointerException(logName + " can not be null.");
        if (str.trim().isEmpty()) throw new IllegalArgumentException(logName + " can not be empty strings.");
    }

    public static void validateString(String logName, String str, int minLength) {
        validateString(logName, str);
        if (str.trim().length() < minLength) throw new IllegalArgumentException(logName + " must have at least " + minLength + " characters.");
    }

    public static void validateList(List<Object> list, String listName) {
        if (list == null) throw new NullPointerException("'" + listName + "' can not be null.");
        if (list.isEmpty()) throw new NullPointerException("'" + listName + "' can not be empty.");
    }
}
