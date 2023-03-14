package by.leverx.googleDrive.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

  private int statusCode;
  private String message;

  public ErrorResponse(HttpStatus statusCode, String message) {
    super();
    this.message = message;
  }
}
