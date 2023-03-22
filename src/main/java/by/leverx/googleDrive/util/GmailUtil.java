package by.leverx.googleDrive.util;

import static java.lang.String.format;

import by.leverx.googleDrive.dto.DtoMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

public class GmailUtil {

  private static String FIRST_MESSAGE_TEMPLATE =
      "Dear %s!\n"
          + "      We inform you that on %s at %s you are invited to conduct an assessment with %s."
          + " The assessment will cover the next topics: %s. \n "
          + "      You can discuss with %s the details of meeting by %s address.\n"
          + " Thank you %s for attention!";

  private static String SECOND_MESSAGE_TEMPLATE =
      "To %s!\n"
          + "      We inform you that on %s at %s you will be conducting an assessment with %s. The "
          + "assessment need to be conducted on th following topics: %s. \n"
          + "      Pleas report to %s by %s  address if you have additional information.\n"
          + "Thank you! Have a nice day!";

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
}
