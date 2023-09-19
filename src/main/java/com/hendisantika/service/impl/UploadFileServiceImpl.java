package com.hendisantika.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 14/09/21
 * Time: 06.58
 */
@Service
public class UploadFileServiceImpl {
    private final String UPLOADS_FOLDER = "uploads";
    private final Logger log = LoggerFactory.getLogger(getClass());

    public Resource load(String filename) throws MalformedURLException {
        //We load the path of the required image
        Path photoPath = getPath(filename);
        log.info("photoPath: " + photoPath);
        Resource resource = null;
        resource = new UrlResource(photoPath.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            //If we try to load an image that does not exist we throw an exception
            throw new RuntimeException("Error: Image cannot be loaded: " + photoPath);
        }
        return resource;
    }

    public String copy(MultipartFile file) throws IOException {
        String photoName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path _rootPath = getPath(photoName);
        log.info("_rootPath: " + _rootPath);
        //Copy photo to path within the project
        Files.copy(file.getInputStream(), _rootPath);
        return photoName;
    }

    public boolean delete(String filename) {
        //We erase the image of the client
        Path rootPath = getPath(filename);
        File file = rootPath.toFile();
        if (file.exists() && file.canRead()) {
            return file.delete();
        }
        return false;
    }

    public Path getPath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
    }

    public void init() throws IOException {
        Files.createDirectories(Paths.get(UPLOADS_FOLDER));
    }
}
