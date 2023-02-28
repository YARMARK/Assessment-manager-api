package by.leverx.googleDrive.exception;

import static java.lang.String.format;

public class SuchEmployeeAlreadyExist extends RuntimeException {

  public static final String MESSAGE_NAMES = "Employee: %s %s already created!";

  public SuchEmployeeAlreadyExist(String first, String lastName) {

    super(format(MESSAGE_NAMES,first,lastName));
  }
}
