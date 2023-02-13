package by.leverx.googleTest.exception;

public class EmployeeNotFoundException extends NullPointerException{

  public EmployeeNotFoundException(String s) {
    super(s);
  }

  public EmployeeNotFoundException() {
  }
}
