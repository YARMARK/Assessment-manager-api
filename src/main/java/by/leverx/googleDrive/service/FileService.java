package by.leverx.googleDrive.service;

import java.net.URISyntaxException;
import java.util.List;

public interface FileService {

  List<String> getFileNames(String fileExtension) throws URISyntaxException;
}
