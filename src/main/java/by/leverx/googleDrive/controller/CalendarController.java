package by.leverx.googleDrive.controller;

import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_CALENDAR_TAG;

import by.leverx.googleDrive.dto.AssessmentInfoDto;
import by.leverx.googleDrive.facade.CalendarFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/event")
@Api(tags = {SWAGGER_CALENDAR_TAG})
public class CalendarController {

  private CalendarFacade facade;

  @Autowired
  public CalendarController(CalendarFacade facade) {
    this.facade = facade;
  }

  @PostMapping("/create")
  @ApiOperation("create calendar event.")
  public void createEvent(
      @ApiParam("Assessment info for creation event.") @RequestBody AssessmentInfoDto dto)
      throws MessagingException, GeneralSecurityException, IOException, URISyntaxException {
    facade.createEvent(dto);
  }


}
