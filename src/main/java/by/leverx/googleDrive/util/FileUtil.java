package by.leverx.googleDrive.util;

import by.leverx.googleDrive.service.serviceImpl.GoogleServiceImpl;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

  public static List<File> getListOfFiles(String folderName,String fileExtension) throws URISyntaxException {
    var folder = new File(getFolderPath(folderName));
    File[] listOfFiles = folder.listFiles();
    List<File> returnList = new ArrayList<>();
    for (File file : listOfFiles) {
      if (file.isFile() && file.getName().endsWith(fileExtension)) {
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
      throw new IllegalArgumentException("Folder not found in classpath: " + folderName);
    }
    String folderPath = Paths.get(folderUrl).toString();
    return folderPath;
  }
}
