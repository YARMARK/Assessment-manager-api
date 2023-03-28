package by.leverx.googleDrive.service;

import by.leverx.googleDrive.dto.AssessmentInfoDto;
import com.google.api.services.calendar.model.Event;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface CalendarService {

  public Event createEvent(AssessmentInfoDto dto) throws GeneralSecurityException, IOException;

}
