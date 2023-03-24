package by.leverx.googleDrive.service.manager;

import static by.leverx.googleDrive.util.ConstantMessage.getCalendarManagerCredentials;
import static by.leverx.googleDrive.util.ConstantMessage.getCalendarManagerTokensDirectoryPath;
import static by.leverx.googleDrive.util.ConstantMessage.getDriveManagerApplicationName;
import static by.leverx.googleDrive.util.ConstantMessage.getDriveManagerFileNotFoundMessage;
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
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
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
public class CalendarServiceManger {

  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

  private static final List<String> SCOPES =
      Collections.singletonList(CalendarScopes.CALENDAR);

  public Calendar getCalendarService() throws IOException, GeneralSecurityException {
//    GoogleCredentials credentials = GoogleCredentials.fromStream(
//            CalendarServiceManger.class.getResourceAsStream(getDriveServiceAccountCredentials()))
//        .createScoped(SCOPES);

//    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    Calendar calendarService = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
        getCred(HTTP_TRANSPORT))
        .setApplicationName(getDriveManagerApplicationName())
        .build();
    return calendarService;
  }

  private Credential getCred(final NetHttpTransport HTTP_TRANSPORT)
      throws IOException {
    InputStream in = CalendarServiceManger.class.getResourceAsStream(
        getCalendarManagerCredentials());
    if (in == null) {
      throw new FileNotFoundException(
          format(getDriveManagerFileNotFoundMessage(), getCalendarManagerCredentials()));
    }
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(
            new FileDataStoreFactory(new File(getCalendarManagerTokensDirectoryPath())))
        .setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

    return credential;
  }
}
