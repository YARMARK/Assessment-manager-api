package by.leverx.googleDrive.exception;

import static java.lang.String.*;

import by.leverx.googleDrive.util.ConstantMessage;

public class FolderNotFoundException extends RuntimeException{

  public FolderNotFoundException() {
  }

  public FolderNotFoundException(String message) {
    super(format(ConstantMessage.getFolderNotFoundErrorMessage(),message));
  }
}
