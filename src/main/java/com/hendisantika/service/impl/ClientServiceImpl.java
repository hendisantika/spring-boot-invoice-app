package com.hendisantika.service.impl;

import com.hendisantika.model.Client;
import com.hendisantika.model.Invoice;
import com.hendisantika.model.Product;
import com.hendisantika.repository.ClientRepository;
import com.hendisantika.repository.InvoiceRepository;
import com.hendisantika.repository.ProductRepository;
import com.hendisantika.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 14/09/21
 * Time: 06.51
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    private final ProductRepository productRepository;

    private final InvoiceRepository invoiceRepository;

    public ClientServiceImpl(ClientRepository clientRepository, ProductRepository productRepository, InvoiceRepository invoiceRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client findOne(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client fetchByIdWithInvoice(Long id) {
        return clientRepository.fetchByIdWithInvoice(id);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Page<Client> findAll(Pageable page) {
        return clientRepository.findAll(page);
    }

    @Override
    public List<Product> findByName(String search) {
        return productRepository.findByNameLikeIgnoreCase("%" + search + "%");
    }

    @Override
    public void saveInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public Invoice fetchByIdWithClientWithInvoiceLineWithProduct(Long id) {
        return invoiceRepository.fetchByIdWithClientWithInvoiceLineWithProduct(id);
    }

}
