package com.hendisantika.controller;

import com.hendisantika.model.Client;
import com.hendisantika.model.Invoice;
import com.hendisantika.model.Product;
import com.hendisantika.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/form/{clientId}")
    public String create(@PathVariable(value = "clientId") Long clientId, Map<String, Object> model,
                         RedirectAttributes flash) {
        Client client = clientService.findOne(clientId);
        if (client == null) {
            flash.addFlashAttribute("error", "There is no such client");
            return "redirect:/clients";
        }
        Invoice invoice = new Invoice();
        invoice.setClient(client);
        model.put("invoice", invoice);
        model.put("title", "Create invoice");

        return "/invoices/form";
    }

    @GetMapping(value = "/load-product/{search}", produces = {"application/json"})
    public @ResponseBody
    List<Product> loadProducts(@PathVariable String search) {
        return clientService.findByName(search);
    }
}
