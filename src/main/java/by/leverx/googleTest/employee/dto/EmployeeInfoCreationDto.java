package by.leverx.googleTest.employee.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeInfoCreationDto {

//  @NotBlank(message = "ENTER VALID FIRST NAME, THIS FIELD CAN'T BE BLANK")
//  @Size(min = 2, max = 20, message = "SIZE MUST BE BETWEEN 2 AND 20 SYMBOLS")
//  @Pattern(regexp = "[A-Z][a-z]{2,19}", message = "INVALID FIRST NAME VALUE")
  private String firstName;

//  @NotBlank(message = "ENTER VALID LAST NAME, THIS FIELD CAN'T BE BLANK")
//  @Size(min = 2, max = 30, message = "SIZE MUST BE BETWEEN 2 AND 30 SYMBOLS")
//  @Pattern(regexp = "[A-Z][a-z]{2,5}([a-z]{1,24}|\\-[A-Z][a-z]{2,24})", message = "INVALID LAST NAME VALUE")
  private String lastName;

  private String email;

  private String title;

  private Boolean needAssessment = Boolean.FALSE;
}
