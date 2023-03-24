package by.leverx.googleDrive.controller;

import by.leverx.googleDrive.facade.CalendarFacade;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/event")
public class CalendarController {

  private CalendarFacade facade;

  @Autowired
  public CalendarController(CalendarFacade facade) {
    this.facade = facade;
  }

  @PostMapping("/create")
  public void createEvent()
      throws MessagingException, GeneralSecurityException, IOException, URISyntaxException {
    facade.createEvent();
  }

}
