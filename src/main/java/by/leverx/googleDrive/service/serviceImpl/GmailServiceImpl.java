package by.leverx.googleDrive.service.serviceImpl;

import static by.leverx.googleDrive.util.GmailUtil.createMessageFirstForm;
import static jakarta.mail.Message.RecipientType.*;

import by.leverx.googleDrive.dto.DtoMessage;
import by.leverx.googleDrive.service.GmailService;
import by.leverx.googleDrive.service.manager.ServiceGmailManager;
import com.google.api.services.gmail.model.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class GmailServiceImpl implements GmailService {

  private ServiceGmailManager gmailManager;

  private String EMAIL_VALUE = "service-account@valued-range-374308.iam.gserviceaccount.com";

  public GmailServiceImpl(ServiceGmailManager gmailManager) {
    this.gmailManager = gmailManager;
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

//  private Properties getProperties(){
//    Properties props = new Properties();
//    props.put("mail.smtp.host","smtp.gmail.com");
//    props.put("mail.smtp.port","587");
//    props.put("mail.smtp.starttls.enable", "true");
//    props.put("mail.smtp.auth", "true");
//    return props;
//  }

}
