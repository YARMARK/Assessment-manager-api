package by.leverx.googleDrive.exception;

import org.springframework.http.HttpStatus;

public class SomethingWentWrongException  extends RuntimeException{

 private HttpStatus statusCode;

  public SomethingWentWrongException(String message) {
    super(message);
  }

  public SomethingWentWrongException(String message,HttpStatus code) {
    super(message);
    this.statusCode = code;
  }

  public HttpStatus getStatusCode() {
    return statusCode;
  }
}
