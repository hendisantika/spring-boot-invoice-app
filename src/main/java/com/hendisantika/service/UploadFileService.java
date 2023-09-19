package com.hendisantika.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface UploadFileService {
    Resource load(String filename);

    String copy(MultipartFile file);

    boolean delete(String filename);

    Path getPath(String filename);

    void deleteAll();

    void init();
}
