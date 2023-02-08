package by.leverx.googleTest.exception.handler;

import by.leverx.googleTest.exception.SomethingWentWrongException;
import by.leverx.googleTest.exception.SuchFolderAlreadyExist;
import by.leverx.googleTest.exception.SuchUserAlreadyExist;
import by.leverx.googleTest.exception.UserNotFoundException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = GoogleJsonResponseException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ErrorResponse handleGoogleJsonResponseException(
      GoogleJsonResponseException ex) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
        "Unable to create folder:" + ex.getDetails());
  }

  @ExceptionHandler(SuchUserAlreadyExist.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ErrorResponse handleSuchUserAlreadyExist(SuchUserAlreadyExist ex) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
  }

  @ExceptionHandler(SuchFolderAlreadyExist.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ErrorResponse handleSuchFolderAlreadyExist(SuchFolderAlreadyExist ex) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
  }

  @ExceptionHandler(SomethingWentWrongException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ErrorResponse handleSomethingWentWrongException(
      SomethingWentWrongException ex) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errorMap = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(),
        error.getDefaultMessage()));
    return errorMap;
  }

  @ExceptionHandler(FileNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public @ResponseBody ErrorResponse handleSuchUserAlreadyExist(FileNotFoundException ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
        "THE SYSTEM CAN NOT FIND THE PATH SPECIFIED");
  }

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public @ResponseBody ErrorResponse handleUserNotFound(UserNotFoundException ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
        ex.getMessage());
  }
}
