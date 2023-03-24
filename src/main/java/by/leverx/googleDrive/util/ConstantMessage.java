package by.leverx.googleDrive.util;

public class ConstantMessage {

  private static String DB_DEV_URL = "jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1";

  private static String DB_TEST_PROD_URL = "jdbc:postgresql://%s:%s/%s";

  private static String DB_CHANGELOG_PATH = "classpath:changelog/db.changelog-master.xml";

  private static String SWAGGER_PROJECT_TITLE = "Assessment manager api";

  private static String SWAGGER_TERM_OF_SERVICE = "Term of services";

  private static String SWAGGER_FULL_NAME = "Yaraslau Markau";

  private static String SWAGGER_E_MAIL = "yaraslau.markau@leverx.com";

  private static String SWAGGER_URL = "https://mail.google.com/";

  private static String SWAGGER_LICENCE = "Apache 2.0";

  private static String SWAGGER_LICENCE_URL = "https://www.apache.org/licenses/LICENSE-2.0.html";

  private static String SWAGGER_PROJECT_DESCRIPTION =
      "Application built on Spring Boot as API-provide to help admin with creating folders and putting"
          + " predefined template files for the next assessment date and sending notifications for"
          + " both sides of the assessment. This application has integration with Google Drive (folders"
          + " and files), JIRA (Employees info like first and last name).";

  private static String SWAGGER_PROJECT_VERSION = "1.0.0";

  private static String GLOBAL_EXCEPTION_GJRE_MESSAGE = "Unable to create file or folder: %s";

  private static String GLOBAL_EXCEPTION_HTTP_CLIENT_ERROR_MESSAGE =
      "You aren't authorized or permissions aren't exist";

  private static String EMPLOYEE_NOT_FOUND_MESSAGE_NAMES = "Employee: %s %s not found!";

  private static String EMPLOYEE_NOT_FOUND_MESSAGE_ID = "Employee with %s Id not found!";

  private static String FOLDER_NOT_FOUND_ERROR_MESSAGE = "Folder %s not found";

  private static String SUCH_EMPLOYEE_ALREADY_EXISTS_MESSAGE_NAMES = "Employee: %s %s already created!";

  private static String DRIVE_MANAGER_APPLICATION_NAME = "Test Application";

  private static String DRIVE_MANAGER_TOKENS_DIRECTORY_PATH = "tokens/DriveTokens";

  private static String GMAIL_MANAGER_TOKENS_DIRECTORY_PATH = "tokens/GmailTokens";

  private static String CALENDAR_MANAGER_TOKENS_DIRECTORY_PATH = "tokens/CalendarTokens";

  private static String DRIVE_MANAGER_CREDENTIALS_FILE = "/credentials/DriveCred.json";

  private static String GMAIL_MANAGER_CREDENTIALS = "/credentials/GmailCred.json";

  private static String CALENDAR_MANAGER_CREDENTIALS = "/credentials/CalendarCred.json";

  private static String DRIVE_MANAGER_FILE_NOT_FOUND_MESSAGE = "Resource not found: %s";

  private static String DRIVE_SERVICE_ACCOUNT_CREDENTIALS = "/credentials/googleServiceCredentials.json";

  private static String EMPLOYEE_UTIL_URL_PREFIX = "https://drive.google.com/drive/folders/";

  private static String EMPLOYEE_UTIL_URL_POSTFIX = "?usp=share_link";

  private static String GOOGLE_UTIL_ILLEGAL_ARG_EXC = "Folder not found in classpath: %s";

  private static String UNABLE_TO_CRETE_UPLOAD = "Unable to create folder or upload file";

  private static String MIME_TYPE_FOLDER = "application/vnd.google-apps.folder";

  private static String MIME_TYPE_FILE = "application/vnd.google-apps.spreadsheet";

  public static String getDbDevUrl() {
    return DB_DEV_URL;
  }

  public static String getDbTestProdUrl() {
    return DB_TEST_PROD_URL;
  }

  public static String getDbChangelogPath() {
    return DB_CHANGELOG_PATH;
  }

  public static String getSwaggerProjectTitle() {
    return SWAGGER_PROJECT_TITLE;
  }

  public static String getSwaggerTermOfService() {
    return SWAGGER_TERM_OF_SERVICE;
  }

  public static String getSwaggerFullName() {
    return SWAGGER_FULL_NAME;
  }

  public static String getSwaggerEMail() {
    return SWAGGER_E_MAIL;
  }

  public static String getSwaggerUrl() {
    return SWAGGER_URL;
  }

  public static String getSwaggerLicence() {
    return SWAGGER_LICENCE;
  }

  public static String getSwaggerLicenceUrl() {
    return SWAGGER_LICENCE_URL;
  }

  public static String getSwaggerProjectDescription() {
    return SWAGGER_PROJECT_DESCRIPTION;
  }

  public static String getSwaggerProjectVersion() {
    return SWAGGER_PROJECT_VERSION;
  }

  public static String getGlobalExceptionGjreMessage() {
    return GLOBAL_EXCEPTION_GJRE_MESSAGE;
  }

  public static String getGlobalExceptionHttpClientErrorMessage() {
    return GLOBAL_EXCEPTION_HTTP_CLIENT_ERROR_MESSAGE;
  }

  public static String getEmployeeNotFoundMessageNames() {
    return EMPLOYEE_NOT_FOUND_MESSAGE_NAMES;
  }

  public static String getEmployeeNotFoundMessageId() {
    return EMPLOYEE_NOT_FOUND_MESSAGE_ID;
  }

  public static String getFolderNotFoundErrorMessage() {
    return FOLDER_NOT_FOUND_ERROR_MESSAGE;
  }

  public static String getSuchEmployeeAlreadyExistsMessageNames() {
    return SUCH_EMPLOYEE_ALREADY_EXISTS_MESSAGE_NAMES;
  }

  public static String getDriveManagerApplicationName() {
    return DRIVE_MANAGER_APPLICATION_NAME;
  }

  public static String getDriveManagerTokensDirectoryPath() {
    return DRIVE_MANAGER_TOKENS_DIRECTORY_PATH;
  }

  public static String getGmailManagerTokensDirectoryPath() {
    return GMAIL_MANAGER_TOKENS_DIRECTORY_PATH;
  }

  public static String getCalendarManagerTokensDirectoryPath() {
    return CALENDAR_MANAGER_TOKENS_DIRECTORY_PATH;
  }

  public static String getDriveManagerCredentialsFile() {
    return DRIVE_MANAGER_CREDENTIALS_FILE;
  }

  public static String getGmailManagerCredentials() {
    return GMAIL_MANAGER_CREDENTIALS;
  }

  public static String getCalendarManagerCredentials() {
    return CALENDAR_MANAGER_CREDENTIALS;
  }

  public static String getDriveManagerFileNotFoundMessage() {
    return DRIVE_MANAGER_FILE_NOT_FOUND_MESSAGE;
  }

  public static String getDriveServiceAccountCredentials() {
    return DRIVE_SERVICE_ACCOUNT_CREDENTIALS;
  }

  public static String getEmployeeUtilUrlPrefix() {
    return EMPLOYEE_UTIL_URL_PREFIX;
  }

  public static String getEmployeeUtilUrlPostfix() {
    return EMPLOYEE_UTIL_URL_POSTFIX;
  }

  public static String getGoogleUtilIllegalArgExc() {
    return GOOGLE_UTIL_ILLEGAL_ARG_EXC;
  }

  public static String getUnableToCreteUploadMessage() {
    return UNABLE_TO_CRETE_UPLOAD;
  }

  public static String getMimeTypeFolder() {
    return MIME_TYPE_FOLDER;
  }

  public static String getMimeTypeFile() {
    return MIME_TYPE_FILE;
  }
}
