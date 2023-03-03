package by.leverx.googleDrive.exception.handler;

import static java.lang.String.format;

import by.leverx.googleDrive.exception.EmployeeNotFoundException;
import by.leverx.googleDrive.exception.NoAuthOrNoPermissionException;
import by.leverx.googleDrive.exception.SomethingWentWrongException;
import by.leverx.googleDrive.exception.SuchEmployeeAlreadyExist;
import by.leverx.googleDrive.exception.SuchFolderAlreadyExist;
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

  private static final String GJRE_MESSAGE = "Unable to create folder: %s";

  @ExceptionHandler(GoogleJsonResponseException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ErrorResponse handleGoogleJsonResponseException(
      GoogleJsonResponseException ex) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
        format(GJRE_MESSAGE, ex.getDetails()));
  }

  @ExceptionHandler(SuchEmployeeAlreadyExist.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ErrorResponse handleSuchEmployeeAlreadyExist(SuchEmployeeAlreadyExist ex) {
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
  public @ResponseBody ErrorResponse handleSuchEmployeeAlreadyExist(FileNotFoundException ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
  }

  @ExceptionHandler(EmployeeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public @ResponseBody ErrorResponse handleEmployeeNotFound(EmployeeNotFoundException ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
        ex.getMessage());
  }

  @ExceptionHandler(NoAuthOrNoPermissionException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public @ResponseBody ErrorResponse handleNoAuthOrPermission(NoAuthOrNoPermissionException ex) {
    return new ErrorResponse(HttpStatus.FORBIDDEN.value(),
        ex.getMessage());
  }
}
