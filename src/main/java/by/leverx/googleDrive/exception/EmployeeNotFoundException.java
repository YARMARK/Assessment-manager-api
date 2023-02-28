package by.leverx.googleDrive.exception;

import static java.lang.String.format;

public class EmployeeNotFoundException extends NullPointerException {

  private static final String MESSAGE_NAMES = "Employee: %s %s not found!";
  private static final String MESSAGE_ID = "Employee with %s Id not found!";


  public EmployeeNotFoundException(String firstName, String lastName) {
    super(format(MESSAGE_NAMES,firstName, lastName));
  }

  public EmployeeNotFoundException(String id) {
    super(format(MESSAGE_ID,id));
  }

  public EmployeeNotFoundException() {
  }
}
