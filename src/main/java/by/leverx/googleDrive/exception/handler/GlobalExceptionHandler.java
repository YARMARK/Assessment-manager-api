package by.leverx.googleDrive.exception.handler;

import static by.leverx.googleDrive.util.ConstantMessage.ExceptionConstant.GLOBAL_EXCEPTION_GJRE_MESSAGE;
import static by.leverx.googleDrive.util.ConstantMessage.ExceptionConstant.GLOBAL_EXCEPTION_HTTP_CLIENT_ERROR_MESSAGE;
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
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(GoogleJsonResponseException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public @ResponseBody ErrorResponse handleGoogleJsonResponseException(
      GoogleJsonResponseException ex) {
    return new ErrorResponse(HttpStatus.FORBIDDEN.value(),
        format(GLOBAL_EXCEPTION_GJRE_MESSAGE, ex.getDetails()));
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
  public @ResponseBody ErrorResponse handleSomethingWentWrongException(
      SomethingWentWrongException ex) {
    if(ex.getStatusCode() == null) {
      return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
    else {
      return new ErrorResponse(ex.getStatusCode().value(), ex.getMessage());
    }
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
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
  }

  @ExceptionHandler(NoAuthOrNoPermissionException.class)
  public @ResponseBody ErrorResponse handleNoAuthOrPermission(NoAuthOrNoPermissionException ex) {
    return new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
  }

  @ExceptionHandler(HttpClientErrorException.class)
  public @ResponseBody ErrorResponse handleHttpClientException(HttpClientErrorException ex) {
    return new ErrorResponse(ex.getStatusCode().value(), GLOBAL_EXCEPTION_HTTP_CLIENT_ERROR_MESSAGE);
  }

}
