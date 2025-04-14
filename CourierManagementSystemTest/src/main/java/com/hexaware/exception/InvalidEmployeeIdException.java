package com.hexaware.exception;

public class InvalidEmployeeIdException extends Exception {

    public InvalidEmployeeIdException(int employeeId) {
        super("Invalid Employee ID: " + employeeId);
    }

    public InvalidEmployeeIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEmployeeIdException() {
        super();
    }

    public InvalidEmployeeIdException(String message) {
        super(message);
    }

    public InvalidEmployeeIdException(Throwable cause) {
        super(cause);
    }

    protected InvalidEmployeeIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
