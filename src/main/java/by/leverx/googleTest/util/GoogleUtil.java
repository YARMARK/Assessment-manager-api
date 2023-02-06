package by.leverx.googleTest.util;

import by.leverx.googleTest.service.GoogleDriveManager;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoogleUtil {

  private GoogleDriveManager manager;

  @Autowired
  public GoogleUtil(GoogleDriveManager manager) {
    this.manager = manager;
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
}
