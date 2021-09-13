package com.hendisantika.repository;

import com.hendisantika.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 14/09/21
 * Time: 06.50
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
