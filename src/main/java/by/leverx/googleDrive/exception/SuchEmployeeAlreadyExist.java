package by.leverx.googleDrive.exception;

import static java.lang.String.format;

import by.leverx.googleDrive.util.ConstantMessage;

public class SuchEmployeeAlreadyExist extends RuntimeException {

  public SuchEmployeeAlreadyExist(String first, String lastName) {

    super(format(ConstantMessage.getSuchEmployeeAlreadyExistsMessageNames(),first,lastName));
  }
}
