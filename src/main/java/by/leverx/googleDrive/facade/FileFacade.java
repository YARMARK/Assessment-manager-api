package by.leverx.googleDrive.facade;

import by.leverx.googleDrive.service.FileService;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileFacade {

  private FileService service;

  @Autowired
  public FileFacade(FileService service) {
    this.service = service;
  }

  public List<String> getTemplates() throws URISyntaxException {
    String fileExtension = "xlsx";
    return service.getFileNames(fileExtension);
  }
}
