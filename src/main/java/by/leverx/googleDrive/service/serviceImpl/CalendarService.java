package by.leverx.googleDrive.service.serviceImpl;

import static java.lang.String.format;

import by.leverx.googleDrive.service.manager.CalendarServiceManger;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Event.Reminders;
import com.google.api.services.calendar.model.EventAttachment;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {

  public static final String ATTACHMENT_URL = "https://docs.google.com/document/d/1nAQ3aPzIZSN8wadjAkYCos4hsjgaw735-YGnfzTs2aA/edit?usp=share_link";

  private static String INVITE_MESSAGE =
      "Dear assessor, you will invited to conduct an assessment. The assessment need to be conducted on "
          + "the following topics. Please check this link to get more info: %s";
  private CalendarServiceManger calendarServiceManger;

  @Autowired
  public CalendarService(CalendarServiceManger calendarManager) {
    this.calendarServiceManger = calendarManager;
  }

  public Event createEvent() throws GeneralSecurityException, IOException {
    EventAttachment eventAttachment = new EventAttachment().setFileUrl(ATTACHMENT_URL);
    Event event = new Event()
        .setSummary("Assessment event")
        .setLocation("Lopationa 7/1, Minsk")
        .setDescription("Conducting an assessment of LeverX Group employees.\n"
            + format(INVITE_MESSAGE, ATTACHMENT_URL))
        .setStart(createEventStart())
        .setEnd(createEventEnd())
        .setAttendees(addAttendee())
        .setReminders(createEventRemainder());
//        .setAttachments(List.of(eventAttachment));
    String calendarId = "primary";
    event = calendarServiceManger.getCalendarService().events().
        insert(calendarId, event)
        .setSendNotifications(true)
        .execute();
    return event;
  }

  public EventDateTime createEventStart() {
    DateTime startDateTime = new DateTime("2023-03-25T17:30:00+03:00");
    EventDateTime start = new EventDateTime()
        .setDateTime(startDateTime)
        .setTimeZone("Europe/Moscow");
    return start;
  }

  public EventDateTime createEventEnd() {
    DateTime endDateTime = new DateTime("2023-03-25T17:35:00+03:00");
    EventDateTime end = new EventDateTime()
        .setDateTime(endDateTime)
        .setTimeZone("Europe/Moscow");
    return end;
  }

  public List<EventAttendee> addAttendee() {
    EventAttendee[] attendees = new EventAttendee[]{
        new EventAttendee().setEmail("yaraslau.markau@leverx.com"),
//        new EventAttendee().setEmail("markovyar7@gmail.com"),
        new EventAttendee().setEmail("ihar.rabtsau@leverx.com")
    };
    attendees[1].setComment("Assessor");
    return Arrays.asList(attendees);
  }

  public Reminders createEventRemainder() {
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
