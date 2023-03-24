package by.leverx.googleDrive.service.manager;

import static by.leverx.googleDrive.util.ConstantMessage.getDriveManagerApplicationName;
import static by.leverx.googleDrive.util.ConstantMessage.getDriveManagerCredentialsFile;
import static by.leverx.googleDrive.util.ConstantMessage.getDriveManagerFileNotFoundMessage;
import static by.leverx.googleDrive.util.ConstantMessage.getDriveManagerTokensDirectoryPath;
import static by.leverx.googleDrive.util.ConstantMessage.getDriveServiceAccountCredentials;
import static by.leverx.googleDrive.util.ConstantMessage.getGmailManagerCredentials;
import static by.leverx.googleDrive.util.ConstantMessage.getGmailManagerTokensDirectoryPath;
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
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import org.springframework.stereotype.Service;

@Service
public class ServiceGmailManager {

  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

  public Gmail getGmailService() throws IOException, GeneralSecurityException {
//    GoogleCredentials credentials = GoogleCredentials.fromStream(
//            ServiceGmailManager.class.getResourceAsStream(getDriveServiceAccountCredentials()))
//        .createScoped(Collections.singleton(GmailScopes.GMAIL_SEND));

//    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    Gmail gmailService = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY,
        getCredentials(HTTP_TRANSPORT))
        .setApplicationName(getDriveManagerApplicationName())
        .build();
    return gmailService;
  }

  private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
      throws IOException {
    InputStream in = ServiceGmailManager.class.getResourceAsStream(
        getGmailManagerCredentials());
    if (in == null) {
      throw new FileNotFoundException(
          format(getDriveManagerFileNotFoundMessage(), getGmailManagerCredentials()));
    }
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, Collections.singleton(GmailScopes.GMAIL_SEND))
        .setDataStoreFactory(
            new FileDataStoreFactory(new File(getGmailManagerTokensDirectoryPath())))
        .setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

    return credential;
  }
}
