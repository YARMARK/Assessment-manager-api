package by.leverx.googleDrive.exception;

import static by.leverx.googleDrive.util.ConstantMessage.ExceptionConstant.FOLDER_NOT_FOUND_ERROR_MESSAGE;
import static java.lang.String.format;

public class FolderNotFoundException extends RuntimeException{

  public FolderNotFoundException() {
  }

  public FolderNotFoundException(String message) {
    super(format(FOLDER_NOT_FOUND_ERROR_MESSAGE,message));
  }
}
