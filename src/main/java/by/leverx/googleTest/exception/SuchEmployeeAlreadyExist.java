package by.leverx.googleTest.exception;

public class SuchUserAlreadyExist  extends RuntimeException{

  public SuchUserAlreadyExist(String message) {
    super(message);
  }

  public SuchUserAlreadyExist() {
  }
}
