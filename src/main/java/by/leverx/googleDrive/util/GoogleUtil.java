package by.leverx.googleDrive.util;

import static by.leverx.googleDrive.util.ConstantMessage.getGoogleUtilIllegalArgExc;
import static by.leverx.googleDrive.util.ConstantMessage.getMimeTypeFile;
import static by.leverx.googleDrive.util.ConstantMessage.getMimeTypeFolder;
import static java.lang.String.format;

import by.leverx.googleDrive.service.serviceImpl.GoogleServiceImpl;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GoogleUtil {

  public static String GOOGLE_UTIL_ILLEGAL_ARG_EXC = "Folder not found in classpath: %s";

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

  public static List<File> getListOfFiles(String folderName) throws URISyntaxException {
    var folder = new java.io.File(getFolderPath(folderName));
    java.io.File[] listOfFiles = folder.listFiles();
    List<java.io.File> returnList = new ArrayList<>();
    for (java.io.File file : listOfFiles) {
      if (file.isFile() && file.getName().endsWith(".xlsx")) {
        returnList.add(file);
      }
    }
    return returnList;
  }

  public static String getFolderPath(String folderName) throws URISyntaxException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (classLoader == null) {
      classLoader = GoogleServiceImpl.class.getClassLoader();
    }
    URI folderUrl = classLoader.getResource(folderName).toURI();
    if (folderUrl == null) {
      throw new IllegalArgumentException(format(getGoogleUtilIllegalArgExc(),folderName));
    }
    String folderPath = Paths.get(folderUrl).toString();
    return folderPath;
  }

  public static String getMimeType(String type) {
    String mimeType = null;
    if (type.equalsIgnoreCase("folder")) {
      mimeType = getMimeTypeFolder();

    } else if (type.equalsIgnoreCase("file")) {
      mimeType = getMimeTypeFile();
    }
    return mimeType;
  }
}
