package com.hendisantika.repository;

import com.hendisantika.model.Client;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 14/09/21
 * Time: 06.44
 */
@Repository
public class ClientRepository2 {
    @PersistenceContext
    private EntityManager em;

    public Client findOne(Long id) {
        return em.find(Client.class, id);
    }

    public List<Client> findAll() {
        return em.createQuery("from Client").getResultList();
    }

    public void save(Client client) {
        if (client.getId() != null && client.getId() > 0) {
            em.merge(client);
        } else {
            em.persist(client);
        }
    }

    public void delete(Long id) {
        if (id != null && id > 0) {
            em.remove(findOne(id));
        }
    }
}
