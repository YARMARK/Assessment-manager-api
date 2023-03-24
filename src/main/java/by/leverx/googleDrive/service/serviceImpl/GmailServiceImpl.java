package by.leverx.googleDrive.service.serviceImpl;

import static by.leverx.googleDrive.util.GmailUtil.createMessageFirstForm;
import static jakarta.mail.Message.RecipientType.TO;

import by.leverx.googleDrive.dto.DtoMessage;
import by.leverx.googleDrive.service.GmailService;
import by.leverx.googleDrive.service.manager.ServiceGmailManager;
import by.leverx.googleDrive.util.GmailUtil;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.gmail.model.Message;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GmailServiceImpl implements GmailService {

  private ServiceGmailManager gmailManager;

  private GoogleServiceImpl googleService;

  private String EMAIL_VALUE = "service-account@valued-range-374308.iam.gserviceaccount.com";

  @Value("${templates.env.folder}")
  private  String FOLDER_NAME;

  private String FILE_EXTENSION = ".docx";


  public GmailServiceImpl(ServiceGmailManager gmailManager, GoogleServiceImpl googleService) {
    this.gmailManager = gmailManager;
    this.googleService = googleService;
  }

  @Override
  public void sendEmail(DtoMessage info, String subject)
      throws MessagingException, IOException, GeneralSecurityException {
    String bodyText = createMessageFirstForm(info);
    MimeMessage email = createEmail(info.getRecipientEmail(), info.getFromEmail(), subject,
        bodyText);
    Message messageWithEmail = createMessageWithEmail(email);
    Message message = gmailManager.getGmailService().users().messages()
        .send(info.getFromEmail(), messageWithEmail)
        .execute();
  }

  @Override
  public void sendInvite(Event event)
      throws MessagingException, GeneralSecurityException, IOException, URISyntaxException {
    List<String> emails = getEmails(event);
    for (String email : emails) {
      MimeMessage inviteMessage = createInviteMessage(event, email);
      Message messageWithEmail = createMessageWithEmail(inviteMessage);
      Message me = gmailManager.getGmailService().users().messages().send("me", messageWithEmail)
          .execute();
    }
  }

  private MimeMessage createEmail(String recipientEmail, String fromEmail, String subject,
      String bodyText) throws MessagingException {

    Properties props = new Properties();

    Session session = Session.getDefaultInstance(props, null);

    MimeMessage email = new MimeMessage(session);
    email.setFrom(new InternetAddress(fromEmail));
    email.addRecipient(TO, new InternetAddress(recipientEmail));
    email.setSubject(subject);
    email.setText(bodyText);
    return email;
  }

  private MimeMessage createInviteMessage(Event event, String email)
      throws MessagingException, URISyntaxException {
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);
//    String inviteSubjectSubject = createInviteSubjectSubject(event, email);
    String inviteSubjectSubject = event.getSummary();
    String inviteMessage = GmailUtil.createInviteMessage(event, email);
    Multipart multipart = getMultipart(event, inviteMessage);
    MimeMessage emailMessage = new MimeMessage(session);
    emailMessage.setFrom(new InternetAddress("yaraslau.markau@leverx.com"));
    emailMessage.addRecipient(TO, new InternetAddress(email));
    emailMessage.setSubject(inviteSubjectSubject);
    emailMessage.setContent(multipart);
    return emailMessage;
  }

  private Multipart getMultipart(Event event, String message)
      throws MessagingException, URISyntaxException {
    Multipart multipart = new MimeMultipart();
    BodyPart messageBodyPart = createMessageBodyPart(message);
    multipart.addBodyPart(messageBodyPart);
    List<File> listOfFiles = googleService.getListOfFiles(FOLDER_NAME, FILE_EXTENSION);
    for(File file : listOfFiles){
      BodyPart fileBodyPart = createFileBodyPart(file.getAbsolutePath());
      multipart.addBodyPart(fileBodyPart);
    }
    return multipart;
  }

  public BodyPart createMessageBodyPart(String message) throws MessagingException {
    BodyPart messageBodyPart = new MimeBodyPart();
    messageBodyPart.setContent(message, "text/plain; charset=utf-8");
    return messageBodyPart;
  }

  public BodyPart createFileBodyPart(String filePath) throws MessagingException {
    BodyPart fileBodyPart = new MimeBodyPart();
    File file = new File(filePath);
    DataSource source = new FileDataSource(file);
    fileBodyPart.setDataHandler(new DataHandler(source));
    fileBodyPart.setFileName(file.getName());
    return fileBodyPart;
  }

  private Message createMessageWithEmail(MimeMessage emailContent)
      throws MessagingException, IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    emailContent.writeTo(buffer);
    byte[] bytes = buffer.toByteArray();
    String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
    Message message = new Message();
    message.setRaw(encodedEmail);
    return message;
  }

  private List<String> getEmails(Event event) {
    List<String> mails = new ArrayList<>();
    List<EventAttendee> attendees = event.getAttendees();
    attendees.forEach(tmp -> mails.add(tmp.getEmail()));
    return mails;
  }

//  private Address[] getRecipients(Event event) throws MessagingException {
//    List<String> emails = getEmails(event);
//    Address[] recipientAddresses = new Address[emails.size()];
//    for (int i = 0; i < emails.size(); i++) {
//      recipientAddresses[i] = new InternetAddress(emails.get(i));
//    }
//    return recipientAddresses;
//  }
}
