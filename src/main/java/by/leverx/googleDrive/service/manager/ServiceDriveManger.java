package by.leverx.googleDrive.service.manager;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import org.springframework.stereotype.Service;

@Service
public class ServiceDriveManger {

  private static final String APPLICATION_NAME = "Test Application";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String SERVICE_ACCOUNT_FILE = "/googleServiceCred.json";

  public Drive getDriveService() throws IOException, GeneralSecurityException {
    GoogleCredentials credentials = GoogleCredentials.fromStream(
        ServiceDriveManger.class.getResourceAsStream(SERVICE_ACCOUNT_FILE)
    ).createScoped(Collections.singleton(DriveScopes.DRIVE));

    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    Drive driveService = new Drive.Builder(httpTransport, JSON_FACTORY,
        new HttpCredentialsAdapter(credentials))
        .setApplicationName(APPLICATION_NAME)
        .build();
    return driveService;
  }
}
