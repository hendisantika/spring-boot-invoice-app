package com.hendisantika.service.impl;

import com.hendisantika.service.UploadFileService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UploadFileServiceImpl implements UploadFileService {
    private static final String UPLOADFOLDER = "uploads";

    @Override
    public Resource load(String filename) {
        //We load the path of the required image
        Path photoPath = getPath(filename);
        log.info("photoPath: {}", photoPath);
        Resource resource;
        try {
            resource = new UrlResource(photoPath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if (!resource.exists() || !resource.isReadable()) {
            //If we try to load an image that does not exist we throw an exception
            throw new RuntimeException("Error: Image cannot be loaded: " + photoPath);
        }
        return resource;
    }


    @Override
    public String copy(MultipartFile file) {
        String photoName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path rootPath = getPath(photoName);
        log.info("_rootPath: {}", rootPath);
        //Copy photo to path within the project
        try {
            Files.copy(file.getInputStream(), rootPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return photoName;
    }

    @Override
    public boolean delete(String filename) {
        //We erase the image of the client
        Path rootPath = getPath(filename);
        File file = rootPath.toFile();
        if (file.exists() && file.canRead()) {
            return file.delete();
        }
        return false;
    }

    @Override
    public Path getPath(String filename) {
        return Paths.get(UPLOADFOLDER).resolve(filename).toAbsolutePath();
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADFOLDER).toFile());
    }


    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(UPLOADFOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
