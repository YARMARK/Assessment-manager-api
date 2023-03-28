package by.leverx.googleDrive.service.serviceImpl;

import static by.leverx.googleDrive.util.CalendarUtil.creteEventDateTime;
import static java.lang.String.format;

import by.leverx.googleDrive.dto.AssessmentInfoDto;
import by.leverx.googleDrive.service.manager.CalendarServiceManger;
import by.leverx.googleDrive.util.CalendarUtil;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Event.Reminders;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarServiceImpl implements by.leverx.googleDrive.service.CalendarService {

  public static final String ATTACHMENT_URL = "https://docs.google.com/document/d/1nAQ3aPzIZSN8wadjAkYCos4hsjgaw735-YGnfzTs2aA/edit?usp=share_link";

  private static String INVITE_MESSAGE =
      "Dear assessor, you will invited to conduct an assessment with %s %s. The assessment need to be conducted on "
          + "the following topics. Please check this link to get more info: %s";
  private CalendarServiceManger calendarServiceManger;

  @Autowired
  public CalendarServiceImpl(CalendarServiceManger calendarManager) {
    this.calendarServiceManger = calendarManager;
  }

  @Override
  public Event createEvent(AssessmentInfoDto dto) throws GeneralSecurityException, IOException {
    Event event = getEvent(dto.getAssessor(), dto.getAssessmentDateStart(),
        dto.getAssessmentDateEnd(), dto.getTimeZone(), dto.getPersonFirstName(),
        dto.getPersonLastName());
    String calendarId = "primary";
    event = calendarServiceManger.getCalendarService().events().
        insert(calendarId, event)
        .setSendNotifications(true)
        .execute();
    return event;
  }

  private Event getEvent(String assessor, LocalDateTime eventStart, LocalDateTime eventEnd,
      String timeZone, String personFirstName, String personLastName) {
    Event event = new Event()
        .setSummary("Assessment event")
        .setLocation("Lopationa 7/1, Minsk")
        .setDescription("Conducting an assessment of LeverX Group employees.\n"
            + format(INVITE_MESSAGE, personFirstName, personLastName, ATTACHMENT_URL))
        .setStart(createEventTime(eventStart, timeZone))
        .setEnd(createEventTime(eventEnd, timeZone))
        .setAttendees(addAttendee(assessor))
        .setReminders(createEventRemainder());
    return event;
  }

  private EventDateTime createEventTime(LocalDateTime dateTime, String timeZone) {
    DateTime time = CalendarUtil.convert(dateTime);
    EventDateTime start = creteEventDateTime(time, timeZone);
    return start;
  }

  private List<EventAttendee> addAttendee(String email) {
    EventAttendee[] attendees = new EventAttendee[]{
        new EventAttendee().setEmail(email).setComment("Assessor"),
//        new EventAttendee().setEmail("yaraslau.markau@leverx.com").setOrganizer(true)
    };
    return Arrays.asList(attendees);
  }

  private Reminders createEventRemainder() {
    EventReminder[] reminderOverrides = new EventReminder[]{
        new EventReminder().setMethod("email").setMinutes(4 * 60),
//        new EventReminder().setMethod("popup").setMinutes(10),
    };
    Event.Reminders reminders = new Event.Reminders()
        .setUseDefault(false)
        .setOverrides(Arrays.asList(reminderOverrides));
    return reminders;
  }
}
