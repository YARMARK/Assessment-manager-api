package by.leverx.googleTest.exception;

public class UserNotFoundException  extends NullPointerException{

  public UserNotFoundException(String s) {
    super(s);
  }

  public UserNotFoundException() {
  }
}
