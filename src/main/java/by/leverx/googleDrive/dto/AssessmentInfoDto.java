package by.leverx.googleDrive.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AssessmentInfoDto {

  public String assessor;

  public String personFirstName;

  public String personLastName;

  public LocalDateTime assessmentDateStart;

  public LocalDateTime assessmentDateEnd;

  public String timeZone;

}
