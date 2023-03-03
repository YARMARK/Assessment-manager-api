package by.leverx.googleDrive.exception;

public class NoAuthOrNoPermissionException extends RuntimeException{

  public NoAuthOrNoPermissionException(String message) {
    super(message);
  }
}
