package by.leverx.googleDrive.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "EmployeeCreationDto",
    description = "model represents employee info is inserted by user.")
public class EmployeeInfoCreationDto {

  @ApiModelProperty(value = "First Name", name = "firstName", dataType = "String", example = "Alex")
  private String firstName;

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
