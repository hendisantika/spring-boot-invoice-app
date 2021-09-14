package com.hendisantika.controller;

import com.hendisantika.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/09/21
 * Time: 05.55
 */
@Controller
@RequestMapping("/invoice")
@Secured("ROLE_ADMIN")
@SessionAttributes("invoice")
public class InvoiceController {

    @Autowired
    private ClientService clientService;
}
