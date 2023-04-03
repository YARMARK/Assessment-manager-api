package by.leverx.googleDrive.controller;

import static by.leverx.googleDrive.util.ConstantMessage.SwaggerConstant.SWAGGER_FILE_TAG;

import by.leverx.googleDrive.facade.FileFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
@Api(tags = {SWAGGER_FILE_TAG})
public class FileController {

  private FileFacade facade;

  @Autowired
  public FileController(FileFacade facade) {
    this.facade = facade;
  }

  @GetMapping("/templateFiles")
  @ApiOperation("returns list of template names to upload to google Drive.")
  public ResponseEntity<List<String>> getFileList() throws URISyntaxException {
    List<String> listOfFileNames = facade.getTemplates();
    return !listOfFileNames.isEmpty() ?
        ResponseEntity.ok().body(listOfFileNames) :
        ResponseEntity.notFound().build();
  }
}
