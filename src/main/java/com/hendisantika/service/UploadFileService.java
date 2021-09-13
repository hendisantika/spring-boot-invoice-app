package com.hendisantika.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;

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
public class UploadFileService {
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
}
