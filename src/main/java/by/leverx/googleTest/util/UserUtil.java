package by.leverx.googleTest.util;

import by.leverx.googleTest.user.UserInfo;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

  public UserInfo setIncomeAndAssessmentDates(UserInfo userInfo){
    LocalDate dateOfIncoming = LocalDate.now();
    userInfo.setDateOfIncoming(dateOfIncoming);
    userInfo.setLastAssessmentDate(dateOfIncoming);
    LocalDate nextAssessmentDate = dateOfIncoming.plusYears(1);
    userInfo.setNextAssessmentDate(nextAssessmentDate);
    return userInfo;
  }

  public UserInfo setFolderUrl(UserInfo info, String folderId){
    String urlTemplates = "https://drive.google.com/drive/folders/" + folderId + "?usp=share_link";
    info.setFolder_url(urlTemplates);
    return info;
  }
}
