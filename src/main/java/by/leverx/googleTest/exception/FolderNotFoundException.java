package by.leverx.googleTest.exception;

public class FolderNotFoundException extends NullPointerException{

  public FolderNotFoundException() {
  }

  public FolderNotFoundException(String s) {
    super(s);
  }
}
