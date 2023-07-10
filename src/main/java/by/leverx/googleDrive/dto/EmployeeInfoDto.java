package by.leverx.googleDrive.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "EmployeeCreation",
    description = "model represents employee info from database.")
public class EmployeeInfoDto implements Serializable {

  @ApiModelProperty(value = "Fist Name", name = "firstName", dataType = "String", example = "Alex")
  private String firstName;

  @ApiModelProperty(value = "Last Name", name = "lastName", dataType = "String", example = "Soprano")
  private String lastName;

  @ApiModelProperty(value = "E-mail", name = "email", dataType = "String",
      example = "alex.soprano1993@lev.com")
  private String email;

  @ApiModelProperty(value = "Last assessment folder", name = "lastAssessmentFolder",
      dataType = "String", example = "1s5OFxok9n0Vn-MnNPmz-hdT2hn9VRYwg")
  private String lastAssessmentFolder;

  @ApiModelProperty(value = "Tittle", name = "tittle", dataType = "String", example = "en_US")
  private String title;

  @ApiModelProperty(value = "Team name", name = "teamName", dataType = "String", example = "Java team")
  private String teamName;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  @ApiModelProperty(value = "Date of incoming", name = "dateOfIncoming",
      dataType = "LocalDate", example = "28-11-2023")
  private LocalDate dateOfIncoming;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  @ApiModelProperty(value = "Previous assessment date", name = "previousAssessmentDate",
      dataType = "LocalDate", example = "08-11-2023")
  private LocalDate previousAssessmentDate;


  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  @ApiModelProperty(value = "Next assessment date", name = "nextAssessmentDate",
      dataType = "LocalDate", example = "08-11-2023")
  private LocalDate nextAssessmentDate;

  @ApiModelProperty(value = "Need Assessment", name = "needAssessment", dataType = "Boolean",
      example = "false")
  private Boolean needAssessment;
}
