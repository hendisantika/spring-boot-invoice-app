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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
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
    @GetMapping(value = "/view/{id}")
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
        return "view";
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

        //We check if the user has the necessary role for this resource
        if (hasRole("ROLE_ADMIN")) {
            log.info("The user has the necessary role to access this resource");
        } else {
            log.error("The user does NOT have the necessary role to access this resource");
        }
        //In this way we can do the same, without having to implement the hasRole () method
		/*SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper
		(request, "ROLE_");
		if(securityContext.isUserInRole("ADMIN")) {
			log.info("Using SecurityContextHolderAwareRequestWrapper: The user has the necessary role to access
to this resource");
		}else {
			log.info("Using SecurityContextHolderAwareRequestWrapper: The user does NOT have the necessary role to
			access this resource");
		}*/
        //This is yet another way to do the same, but using the request object injected to the method
        if (request.isUserInRole("ROLE_ADMIN")) {
            log.info("Using HttpServletRequest: The user has the necessary role to access this resource");
        } else {
            log.info("Using HttpServletRequest: The user does NOT have the necessary role to access this resource");
        }

        Pageable pageRequest = PageRequest.of(page, 3);
        Page<Client> clients = clientService.findAll(pageRequest);
        PageRender<Client> render = new PageRender<>("/clients", clients);
        model.addAttribute("title", messageSource.getMessage("text.list.title", null, locale));
        model.addAttribute("clients", clients);
        model.addAttribute("page", render);
        return "/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/form")
    public String create(Map<String, Object> model) {
        Client client = new Client();
        model.put("title", "Client form");
        model.put("client", client);
        return "/form";
    }

    //@Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    //The @PreAuthorize annotation is the same as @Secured, only it allows more control
    @RequestMapping(value = "/form/{id}")
    public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        if (id > 0) {
            Client client = clientService.findOne(id);
            if (client != null) {
                model.put("title", "Edit customer");
                model.put("client", client);
                return "/form";
            } else {
                flash.addFlashAttribute("error", "The ID is not valid");
                return "redirect:/clients";
            }
        } else {
            flash.addFlashAttribute("error", "The ID has to be positive");
            return "redirect:/clients";
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/form")
    public String save(@Valid Client client, BindingResult result, Model model,
                       @RequestParam("file") MultipartFile photo, RedirectAttributes flash,
                       SessionStatus sessionStatus) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Client form");
            return "/form";
        }
        if (!photo.isEmpty()) {
            /*
             * The following two lines could be used to
             * save the uploaded images to a folder inside
             * of the project structure, but this is
             * advised against. It is best to undock it and save
             * the files in an external folder.
             */
            //Path resources = Paths.get("src/main/resources/static/uploads");
            //String rootPath = resources.toFile().getAbsolutePath();
            //String rootPath = "C://Temp//uploads";

            //We add a "unique id" to the image name
            //to make sure they are not repeated

            //If the user already had a photo, we delete the old one
            if (client.getId() != null && client.getId() > 0
                    && client.getPhoto() != null
                    && client.getPhoto().length() > 0) {
                uploadFileService.delete(client.getPhoto());
            }
            String uniqueFileName = null;
            try {
                uniqueFileName = uploadFileService.copy(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }
            flash.addFlashAttribute(
                    "info",
                    "Image uploaded successfully (" + uniqueFileName + ")");
            client.setPhoto(uniqueFileName);
        }
        String message = client.getId() != null ? "Customer edited successfully " : " Customer created successfully";
        clientService.save(client);
        sessionStatus.setComplete();
        flash.addFlashAttribute("success", message);
        return "redirect:clients";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/remove/{id}")
    public String remove(@PathVariable(value = "id") Long id, RedirectAttributes flash, Map<String, Object> model) {
        if (id > 0) {
            Client client = clientService.findOne(id);
            clientService.delete(id);
            flash.addFlashAttribute("success", "Successfully removed customer");
            if (uploadFileService.delete(client.getPhoto())) {
                flash.addFlashAttribute("info", "Foto " + client.getPhoto() + " successfully removed");
            }
        }
        return "redirect:/clients";
    }

    /*
     * This method allows you to have more control over the roles of the user, being able to access each of them
     */
    private boolean hasRole(String role) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Authentication auth = context.getAuthentication();
            if (auth != null) {
                Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
				/*for(GrantedAuthority authority : authorities) {
					if(role.equals(authority.getAuthority())) {
						return true;
					}
				}*/
                return authorities.contains(new SimpleGrantedAuthority(role));    //This form is more concise than
                // using the for
            }
        }
        return false;
    }
}
