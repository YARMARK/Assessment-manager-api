package by.leverx.googleTest.exception;

public class SomethingWentWrongException  extends RuntimeException{

  public SomethingWentWrongException(String message) {
    super(message);
  }

  public SomethingWentWrongException() {
  }
}
