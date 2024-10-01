package com.suwan.toast.controller.editor;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class EditorImageController {
  private static final String FILE_UPLOAD_PATH = "C:" + File.separator + "files" + File.separator;


  @PostMapping("/board/images")
  public ResponseEntity<List<String>> saveImages(@RequestParam("files") List<MultipartFile> files) throws IOException {
    LocalDate now = LocalDate.now();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy" + File.separator + "MM" + File.separator + "dd");
    String format = dtf.format(now);

    String filepath = format + File.separator;
    String pullPath = FILE_UPLOAD_PATH + File.separator + filepath;
    File file = new File(pullPath);

    if (!file.exists()) file.mkdirs();

    List<String> saveFileNames = new ArrayList<>();
    for (MultipartFile mf : files) {
      String originalFilename = mf.getOriginalFilename();
      String saveName = UUID.randomUUID().toString().substring(0, 8) + "-" + originalFilename;
      File f = new File(pullPath, saveName);

      mf.transferTo(f);

      saveFileNames.add(filepath + saveName);
    }

    return ResponseEntity.ok(saveFileNames);
  }

  @GetMapping("board/images")
  public ResponseEntity<Resource> getImages(@RequestParam("filename") String filename) throws IOException {
    String decodeFilename = URLDecoder.decode(filename);
    Path path = Paths.get(FILE_UPLOAD_PATH, decodeFilename);

    File file = path.toFile();

    Resource resource = new FileSystemResource(file);

    String contentType = Files.probeContentType(path);

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .body(resource);
  }

}
