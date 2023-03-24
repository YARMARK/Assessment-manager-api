package by.leverx.googleDrive.facade;

import by.leverx.googleDrive.dto.DtoMessage;
import by.leverx.googleDrive.service.GmailService;
import com.google.api.services.calendar.model.Event;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GmailFacade {

  private GmailService service;

  @Autowired
  public GmailFacade(GmailService service) {
    this.service = service;
  }

  public void sendMessage(DtoMessage message)
      throws MessagingException, GeneralSecurityException, IOException {
    String subject = "Assessment";
    service.sendEmail(message, subject);
  }

  public void sendInvite(Event event)
      throws MessagingException, GeneralSecurityException, IOException, URISyntaxException {
    service.sendInvite(event);
  }
}
