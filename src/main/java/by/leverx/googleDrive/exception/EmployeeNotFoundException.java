package by.leverx.googleDrive.exception;

import static by.leverx.googleDrive.util.ConstantMessage.getEmployeeNotFoundMessageId;
import static by.leverx.googleDrive.util.ConstantMessage.getEmployeeNotFoundMessageNames;
import static java.lang.String.format;

public class EmployeeNotFoundException extends NullPointerException {

  public EmployeeNotFoundException(String firstName, String lastName) {
    super(format(getEmployeeNotFoundMessageNames(),firstName, lastName));
  }

  public EmployeeNotFoundException(String id) {
    super(format(getEmployeeNotFoundMessageId(),id));
  }

  public EmployeeNotFoundException() {
  }
}
