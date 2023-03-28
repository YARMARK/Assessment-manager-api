package by.leverx.googleDrive.service.serviceImpl;

import static by.leverx.googleDrive.util.FileUtil.getListOfFiles;

import by.leverx.googleDrive.service.FileService;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

  @Value("${templates.env.folder}")
  private String FOLDER_NAME;

  @Override
  public List<String> getFileNames(String fileExtension) throws URISyntaxException {
    List<File> listOfFiles = getListOfFiles(FOLDER_NAME, fileExtension);
    List<String> fileNames = new ArrayList<>();
        listOfFiles.forEach(file ->fileNames.add(file.getName()));
    return fileNames;
  }
}
