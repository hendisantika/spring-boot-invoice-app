package com.hendisantika.repository;

import com.hendisantika.model.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 14/09/21
 * Time: 06.48
 */
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    /**
     * If we use the findById () method of the CrudRepository interface
     * having multiple relationships, too many queries are made
     * to the database. To avoid it, and improve overall performance
     * of the application, we customize the query using joins
     **/
    @Query("select i from Invoice i join fetch i.client c join fetch i.lines l join fetch l.product where i.id=?1")
    Invoice fetchByIdWithClientWithInvoiceLineWithProduct(Long id);

}
