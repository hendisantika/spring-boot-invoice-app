package com.hendisantika.service;

import com.hendisantika.model.Client;
import com.hendisantika.model.Invoice;
import com.hendisantika.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {
    List<Client> findAll();

    void save(Client client);

    Client findOne(Long id);

    Client fetchByIdWithInvoice(Long id);

    void delete(Long id);

    Page<Client> findAll(Pageable page);

    List<Product> findByName(String search);

    void saveInvoice(Invoice invoice);

    Product findProductById(Long id);

    Invoice findInvoiceById(Long id);

    void deleteInvoice(Long id);

    Invoice fetchByIdWithClientWithInvoiceLineWithProduct(Long id);
}
