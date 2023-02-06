package by.leverx.googleTest.exception;

public class SuchFolderAlreadyExist extends RuntimeException {

  public SuchFolderAlreadyExist(String message) {
    super(message);
  }

  public SuchFolderAlreadyExist() {
  }
}
