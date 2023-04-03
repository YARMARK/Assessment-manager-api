package by.leverx.googleDrive.exception;

import static by.leverx.googleDrive.util.ConstantMessage.ExceptionConstant.SUCH_EMPLOYEE_ALREADY_EXISTS_MESSAGE_NAMES;
import static java.lang.String.format;

public class SuchEmployeeAlreadyExist extends RuntimeException {

  public SuchEmployeeAlreadyExist(String first, String lastName) {

    super(format(SUCH_EMPLOYEE_ALREADY_EXISTS_MESSAGE_NAMES,first,lastName));
  }
}
