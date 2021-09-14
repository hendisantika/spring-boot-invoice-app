package com.hendisantika.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/09/21
 * Time: 06.02
 */
@Controller
public class LoginController {

    @GetMapping("/login")    //By default Spring uses
    public String login(Model model,
                        Principal principal,
                        RedirectAttributes flash,
                        @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout) {    //the path / login as a
        // view of the login form
        if (principal != null) {    //User has already logged in
            flash.addFlashAttribute("info", "You have already logged in");
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("error", "Incorrect username and / or password");
        }
        if (logout != null) {
            model.addAttribute("info", "You have logged out");
        }
        return "/login";
    }
}
