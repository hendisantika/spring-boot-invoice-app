package com.hendisantika.service;

import com.hendisantika.model.Client;
import com.hendisantika.repository.ClientRepository;
import com.hendisantika.repository.InvoiceRepository;
import com.hendisantika.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
