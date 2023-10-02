package com.hendisantika.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 14/09/21
 * Time: 06.35
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "invoice_lines")
public class InvoiceLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    /*
     * The @JoinColumn is not necessary here, as by default
     * JPA would create the column with the name "product_id"
     */
    //@JoinColumn(name="product_id")
    private Product product;

    public Double calculatePrice() {
        return quantity.doubleValue() * product.getPrice();
    }
}
