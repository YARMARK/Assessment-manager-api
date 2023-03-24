package by.leverx.googleDrive.util;

import static java.lang.String.format;

import by.leverx.googleDrive.dto.DtoMessage;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

public class GmailUtil {

  private static String FIRST_MESSAGE_TEMPLATE =
      "Dear %s! We inform you that on %s at %s you are invited to conduct an assessment with %s."
          + " The assessment will cover the next topics: %s. \n You can discuss with "
          + "%s the details of meeting by %s address. Thank you %s for attention!";

  private static String SECOND_MESSAGE_TEMPLATE =
      "To %s! We inform you that on %s at %s you will be conducting an assessment with %s. The "
          + "assessment need to be conducted on th following topics: %s. \n Pleas report to %s by %s"
          + " address if you have additional information. Thank you! Have a nice day!";

  private static String INVITE_MESSAGE =
      "To %s!\n\n\n"
          + "      On %s, %s will invited to conduct an assessment.The assessment need to be conducted on "
          + "the following topics: %s.\n"
          + "      Check this link to get event details.\n%s\n\n\n"
          + "Thank you for attention!! Have a nice day!";

  public static String createMessageFirstForm(DtoMessage message) {
    String recipientFullName = performFullName(message.getRecipientFirstName(),
        message.getRecipientLastName());
    String personFullName = performFullName(message.getPersonFirstName(),
        message.getPersonLastName());
    String date = performDate(message.getAssessmentTime())[0];
    String time = performDate(message.getAssessmentTime())[1];
    String personEmail = message.getPersonEmail();
    String topics = performTopicList(message.getTopicList());
    String currentMessage = format(FIRST_MESSAGE_TEMPLATE, recipientFullName, date, time,
        personFullName, topics, personFullName, personEmail, recipientFullName);
    return currentMessage;
  }

  public static String createMessageSecondForm(DtoMessage message) {
    String recipientFullName = performFullName(message.getRecipientFirstName(),
        message.getRecipientLastName());
    String personFullName = performFullName(message.getPersonFirstName(),
        message.getPersonLastName());
    String date = performDate(message.getAssessmentTime())[0];
    String time = performDate(message.getAssessmentTime())[1];
    String personEmail = message.getPersonEmail();
    String topics = performTopicList(message.getTopicList());
    String currentMessage = format(FIRST_MESSAGE_TEMPLATE, recipientFullName, date, time,
        personFullName, topics, personFullName, personEmail);
    return currentMessage;
  }

  public static String createInviteMessage(Event event, String email){
    String date = performDateStartToString(event.getStart().getDateTime());
    String link = event.getHtmlLink();
    String topics = "spring core, database architecture, spring boot framework";
    String message = format(INVITE_MESSAGE, email, date, email, topics, link);
    return message;
  }
//  public static String createInviteSubjectSubject(Event event, String email) {
//    String start = performDateStartToString(event.getStart().getDateTime());
//    String end = performDateEndToString(event.getEnd().getDateTime());
//    String timeZone = event.getStart().getTimeZone();
//    String summary = event.getSummary();
//    String message = "%s @ %s - %s (%s)\n(%s)";
//    return format(message, summary, start, end, timeZone, email);
//  }

  private static String performFullName(String firstName, String lastName) {
    String fullName = firstName.concat(" ").concat(lastName);
    return fullName;
  }

  private static String[] performDate(LocalDateTime dateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm");
    String performedString = dateTime.format(formatter);
    String[] splitDate = performedString.split("-");
    return splitDate;
  }

  private static String performTopicList(List<String> topicList) {
    StringJoiner joiner = new StringJoiner(",");
    topicList.forEach(topic -> joiner.add(topic));
    String topics = joiner.toString();
    return topics;
  }

  private static String performDateStartToString(DateTime dateTime) {
    SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d, yyyy h:mma",
        Locale.ENGLISH);
    String formattedString = formatter.format(dateTime.getValue());
    return formattedString;
  }

  private static String performDateEndToString(DateTime dateTime) {
    SimpleDateFormat formatter = new SimpleDateFormat("h:mma", Locale.ENGLISH);
    String formattedDate = formatter.format(dateTime.getValue());
    return formattedDate;
  }
}
