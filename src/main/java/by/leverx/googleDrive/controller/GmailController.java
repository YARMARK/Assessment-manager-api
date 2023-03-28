package by.leverx.googleDrive.controller;

import by.leverx.googleDrive.config.SwaggerConfig;
import by.leverx.googleDrive.dto.DtoMessage;
import by.leverx.googleDrive.facade.GmailFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {SwaggerConfig.SWAGGER_GMAIL_TAG})
public class GmailController {

  private GmailFacade facade;

  @Autowired
  public GmailController(GmailFacade facade) {
    this.facade = facade;
  }

  @PostMapping("/send")
  @ApiOperation("send messages to dto emails.")
  public void sendMessageToEmail(@RequestBody DtoMessage message)
      throws MessagingException, GeneralSecurityException, IOException {
    facade.sendMessage(message);
  }
}
