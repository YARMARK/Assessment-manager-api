package by.leverx.googleDrive.service.manager;

import static by.leverx.googleDrive.util.ConstantMessage.GoogleConstants.DRIVE_MANAGER_APPLICATION_NAME;
import static by.leverx.googleDrive.util.ConstantMessage.GoogleConstants.DRIVE_MANAGER_CREDENTIALS_FILE;
import static by.leverx.googleDrive.util.ConstantMessage.GoogleConstants.DRIVE_MANAGER_FILE_NOT_FOUND_MESSAGE;
import static by.leverx.googleDrive.util.ConstantMessage.GoogleConstants.DRIVE_MANAGER_TOKENS_DIRECTORY_PATH;
import static java.lang.String.format;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GoogleDriveManager {

  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final List<String> SCOPES =
      Collections.singletonList(DriveScopes.DRIVE);

  public Drive getService() throws GeneralSecurityException, IOException {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
        .setApplicationName(DRIVE_MANAGER_APPLICATION_NAME)
        .build();
    return service;
  }

  private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
      throws IOException {
    InputStream in = GoogleDriveManager.class.getResourceAsStream(
        DRIVE_MANAGER_CREDENTIALS_FILE);
    if (in == null) {
      throw new FileNotFoundException(
          format(DRIVE_MANAGER_FILE_NOT_FOUND_MESSAGE, DRIVE_MANAGER_CREDENTIALS_FILE));
    }
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(
            new FileDataStoreFactory(new File(DRIVE_MANAGER_TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

    return credential;
  }
}
