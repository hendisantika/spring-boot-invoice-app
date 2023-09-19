package com.hendisantika.controller;

import com.hendisantika.model.Client;
import com.hendisantika.model.Invoice;
import com.hendisantika.model.InvoiceLine;
import com.hendisantika.model.Product;
import com.hendisantika.service.impl.ClientServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
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
@RequestMapping("/invoices")
@Secured("ROLE_ADMIN")
@SessionAttributes("invoice")
public class InvoiceController {

    private final ClientServiceImpl clientServiceImpl;

    public InvoiceController(ClientServiceImpl clientServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
    }

    @GetMapping("/form/{clientId}")
    public String create(@PathVariable(value = "clientId") Long clientId, Map<String, Object> model,
                         RedirectAttributes flash) {
        Client client = clientServiceImpl.findOne(clientId);
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
        return clientServiceImpl.findByName(search);
    }

    @PostMapping("/form")
    public String save(
            @Valid Invoice invoice,
            BindingResult result,
            @RequestParam(name = "item_id[]", required = false) Long[] itemId,
            @RequestParam(name = "quantity[]", required = false) Integer[] quantities,
            RedirectAttributes flash,
            SessionStatus status,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("title", "Create Invoice");
            return "/invoices/form";
        }
        if (itemId == null || itemId.length == 0) {
            model.addAttribute("title", "Create Invoice");
            model.addAttribute("error", "The invoice must have products");
            return "/invoices/form";
        }
        for (int i = 0; i < itemId.length; i++) {
            Product product = clientServiceImpl.findProductById(itemId[i]);
            InvoiceLine line = new InvoiceLine();
            line.setQuantity(quantities[i]);
            line.setProduct(product);
            invoice.addLine(line);
        }
        clientServiceImpl.saveInvoice(invoice);
        status.setComplete();
        flash.addFlashAttribute("success", "Invoice created successfully");
        return "redirect:/view/" + invoice.getClient().getId();
    }

    @GetMapping("/view/{id}")
    public String ver(@PathVariable(value = "id") Long id,
                      Model model,
                      RedirectAttributes flash) {
        //Invoice invoice = clientService.findInvoiceById(id);
        Invoice invoice = clientServiceImpl.fetchByIdWithClientWithInvoiceLineWithProduct(id);
        if (invoice == null) {
            flash.addFlashAttribute("error", "The invoice does not exist");
            return "redirect:/clients";
        }
        model.addAttribute("invoice", invoice);
        model.addAttribute("title", "Invoice: ".concat(invoice.getDescription()));
        return "/invoices/view";
    }

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable(value = "id") Long id,
            RedirectAttributes flash) {
        Invoice invoice = clientServiceImpl.findInvoiceById(id);
        if (invoice != null) {
            clientServiceImpl.deleteInvoice(id);
            flash.addFlashAttribute("success", "Invoice deleted successfully");
            return "redirect:/view/" + invoice.getClient().getId();
        } else {
            flash.addAttribute("error", "The invoice does not exist");
            return "redirect:/clients";
        }
    }
}
