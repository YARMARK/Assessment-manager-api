package by.leverx.googleTest.util;

import by.leverx.googleTest.service.GoogleDriveManager;
import by.leverx.googleTest.service.GoogleService;
import com.google.api.services.drive.model.FileList;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GoogleUtil {

  private GoogleDriveManager manager;

  private GoogleService googleService;

  @Autowired
  public GoogleUtil(GoogleDriveManager manager, GoogleService googleService) {
    this.manager = manager;
    this.googleService = googleService;
  }

  public String searchFolderByFolderName(String folderName)
      throws Exception {
    String folderId = null;
    String pageToken = null;
    FileList result = null;
    String query = null;

    if (folderName.isBlank()) {
      System.err.println("Error");
    } else {
      query = " mimeType = 'application/vnd.google-apps.folder'";
    }
    result = manager.getService().files().list().setQ(query)
        .setSpaces("drive")
        .setFields("nextPageToken, files(id, name)")
        .execute();
    for (var file : result.getFiles()) {
      if (file.getName().equals(folderName)) {
        folderId = file.getId();
      }
    }
    return folderId;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void checkCurrentMontFolder() throws Exception {
    LocalDate currentDate = LocalDate.now();
    String currentMonthFolder = currentDate.getYear() + "." + currentDate.getMonthValue();
    String folderId = searchFolderByFolderName(currentMonthFolder);
    if (Objects.isNull(folderId)){
      googleService.createFolderByName(currentMonthFolder);
    }
  }
}
