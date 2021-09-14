package com.hendisantika.service;

import com.hendisantika.model.Role;
import com.hendisantika.model.User;
import com.hendisantika.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 14/09/21
 * Time: 07.01
 */
@Service("jpaUserDetailsService")
public class JPAUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("The user " + username + " no exist!");
            throw new UsernameNotFoundException("The user " + username + " no exist!");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            log.info("User role: " + role.getAuthority());
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        if (authorities.isEmpty()) {
            log.error("User role " + username + " does not have assigned roles!");
            throw new UsernameNotFoundException("User role " + username + " does not have assigned roles!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getEnabled(), true, true, true, authorities);
    }
}
