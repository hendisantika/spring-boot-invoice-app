package com.hendisantika.repository;

import com.hendisantika.model.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 14/09/21
 * Time: 06.46
 */

/*
 * Extending from CrudRepository (Model, PK Type) we can
 * save us implementing the typical CRUD methods.
 * This way we don't need to implement the class
 * We can extend from PagingAndSortingRepository to make it easier
 * the implementation of a paging system
 */
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    @Query("select c from Client c left join fetch c.invoices i where c.id=?1")
    Client fetchByIdWithInvoice(Long id);
}
