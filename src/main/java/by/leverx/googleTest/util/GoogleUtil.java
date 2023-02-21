package by.leverx.googleTest.util;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class GoogleUtil {

  public String creatCurrentMonthFolderName() {
    LocalDate currentDate = LocalDate.now();
    String currentMonthFolder = currentDate.getYear() + "." + currentDate.getMonthValue();
    if (currentDate.getMonthValue() <= 9) {
      currentMonthFolder = currentDate.getYear() + ".0" + currentDate.getMonthValue();
    }
    return currentMonthFolder;
  }

  public String creatNextMonthFolderName() {
    LocalDate currentDate = LocalDate.now();
    String currentMonthFolder =
        currentDate.getYear() + "." + currentDate.getMonth().plus(1).getValue();
    if (currentDate.getMonthValue() <= 9) {
      currentMonthFolder = currentDate.getYear() + ".0" + currentDate.getMonthValue();
    }
    return currentMonthFolder;
  }
}
