package com.hendisantika.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/09/21
 * Time: 06.01
 */
@Controller
public class LocaleController {
    //This method is used to be called by the buttons to change the language of the
    //the navigation bar. In this way, the other parameters that could
    //have in the URL are kept, since if the parameter is added directly
    //?lang=idioma con un href, el resto de parámetros se eliminan
    @GetMapping("/locale")
    public String locale(HttpServletRequest request) {
        String lastUrl = request.getHeader("referer");    //The header 'referer' returns the url of the last page
        return "redirect:".concat(lastUrl);
    }
}
