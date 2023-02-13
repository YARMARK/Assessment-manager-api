package by.leverx.googleTest.exception;

public class SuchEmployeeAlreadyExist extends RuntimeException{

  public SuchEmployeeAlreadyExist(String message) {
    super(message);
  }

  public SuchEmployeeAlreadyExist() {
  }
}
