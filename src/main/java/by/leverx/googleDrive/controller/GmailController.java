package by.leverx.googleDrive.controller;

import by.leverx.googleDrive.dto.DtoMessage;
import by.leverx.googleDrive.facade.GmailFacade;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("email/")
public class GmailController {

  private GmailFacade facade;

  @Autowired
  public GmailController(GmailFacade facade) {
    this.facade = facade;
  }

  @PostMapping("/send")
  public void sendMessageToEmail(@RequestBody DtoMessage message)
      throws MessagingException, GeneralSecurityException, IOException {
    facade.sendMessage(message);
  }
}
