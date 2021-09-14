package com.hendisantika.controller;

import com.hendisantika.model.Client;
import com.hendisantika.service.ClientService;
import com.hendisantika.service.UploadFileService;
import com.hendisantika.util.PageRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.Map;

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

    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        //Client client = clientService.findOne(id);
        Client client = clientService.fetchByIdWithInvoice(id);
        if (client == null) {
            flash.addFlashAttribute("error", "The client does not exist in the database");
            return "redirect:/clients";
        } else {
            model.put("client", client);
            model.put("title", "Customer details - " + client.getName());
        }
        return "/ver";
    }

    @GetMapping(value = {"/clients", "/"})
    public String lists(@RequestParam(name = "page", defaultValue = "0") int page,
                        Model model,
                        Authentication authentication,
                        HttpServletRequest request,
                        Locale locale) {
        if (authentication != null) {
            log.info("The current user is " + authentication.getName());
        }
        /*
         * We can also get access to authentication without injecting it, through the
         * static method SecurityContextHolder.getContext (). getAuthentication ();
         * This allows us to use it in any class
         */

        //Comprobamos si el usuario tiene el rol necesario para este recurso
        if (hasRole("ROLE_ADMIN")) {
            log.info("The user has the necessary role to access this resource");
        } else {
            log.error("The user does NOT have the necessary role to access this resource");
        }
        //In this way we can do the same, without having to implement the hasRole () method
		/*SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper
		(request, "ROLE_");
		if(securityContext.isUserInRole("ADMIN")) {
			log.info("Usando SecurityContextHolderAwareRequestWrapper: El usuario tiene el role necesario para acceder
			 a éste recurso");
		}else {
			log.info("Using SecurityContextHolderAwareRequestWrapper: The user does NOT have the necessary role to
			access this resource");
		}*/
        //This is yet another way to do the same, but using the request object injected to the method
        if (request.isUserInRole("ROLE_ADMIN")) {
            log.info("Using HttpServletRequest: The user has the necessary role to access this resource");
        } else {
            log.info("Usando HttpServletRequest: El usuario NO tiene el role necesario para acceder a éste recurso");
        }

        Pageable pageRequest = PageRequest.of(page, 3);
        Page<Client> clients = clientService.findAll(pageRequest);
        PageRender<Client> render = new PageRender<>("/clients", clients);
        model.addAttribute("title", messageSource.getMessage("text.list.title", null, locale));
        model.addAttribute("clients", clients);
        model.addAttribute("page", render);
        return "/list";
    }

}
