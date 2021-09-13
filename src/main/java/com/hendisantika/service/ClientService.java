package com.hendisantika.service;

import com.hendisantika.model.Client;
import com.hendisantika.model.Product;
import com.hendisantika.repository.ClientRepository;
import com.hendisantika.repository.InvoiceRepository;
import com.hendisantika.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Client> findAll() {
        return (List<Client>) clientRepository.findAll();
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    public Client findOne(Long id) {
        //return clientDao.findOne(id); //Spring Boot 1.5.10
        return clientRepository.findById(id).orElse(null);    //Spring Boot 2
    }

    public Client fetchByIdWithInvoice(Long id) {
        return clientRepository.fetchByIdWithInvoice(id);
    }

    public void delete(Long id) {
        //clientDao.delete(id);
        clientRepository.deleteById(id);    //Spring Boot 2
    }

    public Page<Client> findAll(Pageable page) {
        return clientRepository.findAll(page);
    }

    public List<Product> findByName(String search) {
        return productRepository.findByNameLikeIgnoreCase("%" + search + "%");
    }

}
