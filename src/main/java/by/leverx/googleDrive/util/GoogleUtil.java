package by.leverx.googleDrive.util;

import java.time.LocalDate;

public class GoogleUtil {

  public static String creatCurrentMonthFolderName() {
    LocalDate currentDate = LocalDate.now();
    String currentMonthFolder = currentDate.getYear() + "." + currentDate.getMonthValue();
    if (currentDate.getMonthValue() <= 9) {
      currentMonthFolder = currentDate.getYear() + ".0" + currentDate.getMonthValue();
    }
    return currentMonthFolder;
  }

  public static String creatNextMonthFolderName() {
    LocalDate currentDate = LocalDate.now();
    String currentMonthFolder =
        currentDate.getYear() + "." + currentDate.getMonth().plus(1).getValue();
    if (currentDate.getMonthValue() <= 9) {
      currentMonthFolder = currentDate.getYear() + ".0" + currentDate.getMonthValue();
    }
    return currentMonthFolder;
  }
}
