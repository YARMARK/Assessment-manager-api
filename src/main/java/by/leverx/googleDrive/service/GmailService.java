package by.leverx.googleDrive.service;

import by.leverx.googleDrive.dto.DtoMessage;
import com.google.api.services.calendar.model.Event;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

public interface GmailService {

  void sendEmail(DtoMessage message, String subject)
      throws MessagingException, IOException, GeneralSecurityException;

  public void sendInvite(Event event)
      throws MessagingException, GeneralSecurityException, IOException, URISyntaxException;
}
