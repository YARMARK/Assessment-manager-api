package by.leverx.googleDrive.util;

public class ConstantMessage {

  public static class DatabaseConstant {

    public static final String DB_DEV_URL = "jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1";

    public static final String DB_TEST_PROD_URL = "jdbc:postgresql://%s:%s/%s";

    public static final String DB_CHANGELOG_PATH = "classpath:changelog/db.changelog-master.xml";
  }

  public static class SwaggerConstant {

    public static final String SWAGGER_EMPLOYEE_TAG = "Employee Controller";

    public static final String SWAGGER_DRIVE_TAG = "Drive Controller";

    public static final String SWAGGER_GMAIL_TAG = "Gmail Controller";

    public static final String SWAGGER_CALENDAR_TAG = "Calendar Controller";

    public static final String SWAGGER_FILE_TAG = "File Controller";


    public static final String SWAGGER_EMPLOYEE_TAG_DESCRIPTION =
        "set of endpoints for Creating, Retrieving, and Deleting employees.";

    public static final String SWAGGER_DRIVE_TAG_DESCRIPTION = "set endpoints to manage file in google drive.";

    public static final String SWAGGER_GMAIL_TAG_DESCRIPTION = "set endpoints to manage gmail functions.";

    public static final String SWAGGER_CALENDAR_TAG_DESCRIPTION = "set endpoints to manage calendar function.";

    public static final String SWAGGER_FILE_TAG_DESCRIPTION = "set endpoints to manage files.";

    public static final String SWAGGER_PROJECT_TITLE = "Assessment manager api";

    public static final String SWAGGER_TERM_OF_SERVICE = "Term of services";

    public static final String SWAGGER_FULL_NAME = "Yaraslau Markau";

    public static final String SWAGGER_E_MAIL = "yaraslau.markau@leverx.com";

    public static final String SWAGGER_URL = "https://mail.google.com/";

    public static final String SWAGGER_LICENCE = "Apache 2.0";

    public static final String SWAGGER_LICENCE_URL = "https://www.apache.org/licenses/LICENSE-2.0.html";

    public static final String SWAGGER_PROJECT_DESCRIPTION =
        "Application built on Spring Boot as API-provide to help admin with creating folders and putting"
            + " predefined template files for the next assessment date and sending notifications for"
            + " both sides of the assessment. This application has integration with Google Drive (folders"
            + " and files), JIRA (Employees info like first and last name).";

    public static final String SWAGGER_PROJECT_VERSION = "1.0.0";
  }

  public static class ExceptionConstant {

    public static final String GLOBAL_EXCEPTION_GJRE_MESSAGE = "Unable to create file or folder: %s";

    public static final String GLOBAL_EXCEPTION_HTTP_CLIENT_ERROR_MESSAGE =
        "You aren't authorized or permissions aren't exist";

    public static final String EMPLOYEE_NOT_FOUND_MESSAGE_NAMES = "Employee: %s %s not found!";

    public static final String EMPLOYEE_NOT_FOUND_MESSAGE_ID = "Employee with %s Id not found!";

    public static final String FOLDER_NOT_FOUND_ERROR_MESSAGE = "Folder %s not found";

    public static final String SUCH_EMPLOYEE_ALREADY_EXISTS_MESSAGE_NAMES = "Employee: %s %s already created!";

    public static final String UNABLE_TO_CRETE_UPLOAD = "Unable to create folder or upload file";

    public static final String SOMETHING_WENT_WRONG_MESSAGE = "Something went wrong!";
  }

  public static class GoogleConstants {

    public static final String DRIVE_MANAGER_APPLICATION_NAME = "Test Application";

    public static final String DRIVE_MANAGER_TOKENS_DIRECTORY_PATH = "tokens/DriveTokens";

    public static final String GMAIL_MANAGER_TOKENS_DIRECTORY_PATH = "tokens/GmailTokens";

    public static final String CALENDAR_MANAGER_TOKENS_DIRECTORY_PATH = "tokens/CalendarTokens";

    public static final String DRIVE_MANAGER_CREDENTIALS_FILE = "/credentials/DriveCred.json";

    public static final String GMAIL_MANAGER_CREDENTIALS = "/credentials/GmailCred.json";

    public static final String CALENDAR_MANAGER_CREDENTIALS = "/credentials/CalendarCred.json";

    public static final String DRIVE_MANAGER_FILE_NOT_FOUND_MESSAGE = "Resource not found: %s";

    public static final String DRIVE_SERVICE_ACCOUNT_CREDENTIALS = "/credentials/googleServiceCredentials.json";
  }

  public static class UtilConstant {

    public static final String EMPLOYEE_UTIL_URL_PREFIX = "https://drive.google.com/drive/folders/";

    public static final String EMPLOYEE_UTIL_URL_POSTFIX = "?usp=share_link";

    public static final String GOOGLE_UTIL_ILLEGAL_ARG_EXC = "Folder not found in classpath: %s";

    public static final String MIME_TYPE_FOLDER = "application/vnd.google-apps.folder";

    public static final String MIME_TYPE_FILE = "application/vnd.google-apps.spreadsheet";
  }

}
