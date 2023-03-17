package by.leverx.googleDrive.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class DtoMessage {

  private String fromEmail;

  private String recipientEmail;

  private String recipientFirstName;

  private String recipientLastName;

  private String personEmail;

  private String personFirstName;

  private String personLastName;

  private LocalDateTime assessmentTime;

  private List<String> topicList;

}
