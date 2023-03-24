package by.leverx.googleDrive.facade;

import by.leverx.googleDrive.service.serviceImpl.CalendarService;
import com.google.api.services.calendar.model.Event;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalendarFacade {

  private CalendarService service;

  private GmailFacade facade;


  @Autowired
  public CalendarFacade(CalendarService service, GmailFacade facade) {
    this.service = service;
    this.facade = facade;
  }

  public void createEvent()
      throws GeneralSecurityException, IOException, MessagingException, URISyntaxException {
    Event event = service.createEvent();
//    facade.sendInvite(event);
  }
}
