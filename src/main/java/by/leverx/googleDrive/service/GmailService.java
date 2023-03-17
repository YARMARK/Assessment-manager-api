package by.leverx.googleDrive.service;

import by.leverx.googleDrive.dto.DtoMessage;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GmailService {

  void sendEmail(DtoMessage message, String subject)
      throws MessagingException, IOException, GeneralSecurityException;
}
