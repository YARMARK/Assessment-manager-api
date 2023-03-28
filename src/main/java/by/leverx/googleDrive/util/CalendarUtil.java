package by.leverx.googleDrive.util;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CalendarUtil {

  public static EventDateTime creteEventDateTime(DateTime dateTime, String timeZone) {
    EventDateTime start = new EventDateTime()
        .setDateTime(dateTime)
        .setTimeZone(timeZone);
    return start;
  }

  public static DateTime convert(LocalDateTime localDateTime) {
    long millis = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    return new DateTime(millis);
  }
}
