package by.leverx.googleTest.employee.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "EmployeeCreationDto",
    description = "model represents employee info is inserted by user.")
public class EmployeeInfoCreationDto {

  //  @NotBlank(message = "ENTER VALID FIRST NAME, THIS FIELD CAN'T BE BLANK")
//  @Size(min = 2, max = 20, message = "SIZE MUST BE BETWEEN 2 AND 20 SYMBOLS")
//  @Pattern(regexp = "[A-Z][a-z]{2,19}", message = "INVALID FIRST NAME VALUE")
  @ApiModelProperty(value = "First Name", name = "firstName", dataType = "String", example = "Alex")
  private String firstName;

  //  @NotBlank(message = "ENTER VALID LAST NAME, THIS FIELD CAN'T BE BLANK")
//  @Size(min = 2, max = 30, message = "SIZE MUST BE BETWEEN 2 AND 30 SYMBOLS")
//  @Pattern(regexp = "[A-Z][a-z]{2,5}([a-z]{1,24}|\\-[A-Z][a-z]{2,24})", message = "INVALID LAST NAME VALUE")
  @ApiModelProperty(value = "Last Name", name = "lastName", dataType = "String", example = "Soprano")
  private String lastName;

  @ApiModelProperty(value = "E-mail", name = "email", dataType = "String",
      example = "alex.soprano1993@lev.com")
  private String email;

  @ApiModelProperty(value = "Tittle", name = "tittle", dataType = "String", example = "en_US")
  private String title;

  @ApiModelProperty(value = "Need Assessment", name = "needAssessment", dataType = "Boolean",
      example = "false")
  private Boolean needAssessment = Boolean.FALSE;
}
