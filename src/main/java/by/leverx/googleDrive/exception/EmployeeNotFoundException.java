package by.leverx.googleDrive.exception;

import static by.leverx.googleDrive.util.ConstantMessage.ExceptionConstant.EMPLOYEE_NOT_FOUND_MESSAGE_ID;
import static by.leverx.googleDrive.util.ConstantMessage.ExceptionConstant.EMPLOYEE_NOT_FOUND_MESSAGE_NAMES;
import static java.lang.String.format;

public class  EmployeeNotFoundException extends NullPointerException {

  public EmployeeNotFoundException(String firstName, String lastName) {
    super(format(EMPLOYEE_NOT_FOUND_MESSAGE_NAMES,firstName, lastName));
  }

  public EmployeeNotFoundException(String id) {
    super(format(EMPLOYEE_NOT_FOUND_MESSAGE_ID,id));
  }
}
