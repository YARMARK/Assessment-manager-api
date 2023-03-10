package by.leverx.googleDrive.exception;

import static java.lang.String.*;

public class FolderNotFoundException extends NullPointerException{

  private static final String   ERROR_MESSAGE = "Folder %s not found";
  public FolderNotFoundException() {
  }

  public FolderNotFoundException(String s) {
    super(format(ERROR_MESSAGE,s));
  }
}
