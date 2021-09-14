package com.hendisantika.controller;

import com.hendisantika.service.ClientService;
import com.hendisantika.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.net.MalformedURLException;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/09/21
 * Time: 05.40
 */
@Controller
@SessionAttributes("client")
public class ClientController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    //@Qualifier ("clientDao") If we have several implementations
    //of the interface, we indicate which one we want to use by giving its name
    //If we only have one, we use @Autowired
    @Autowired
    private ClientService clientService;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private MessageSource messageSource;

    //Method for uploading images
    //We write the parameter {filename:. +} As a regular expression
    //since if not Spring would remove the extension of it (from the file)
    //and we need it to find the file on the computer.
    //When the view tries to upload the image from the uploads folder
    //this method is called
    @Secured("ROLE_USER")
    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
        Resource resource = null;
        try {
            resource = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
