package com.hendisantika.config;

import com.hendisantika.auth.handler.LoginSuccessHandler;
import com.hendisantika.service.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/09/21
 * Time: 05.34
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler successHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JPAUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Here we specify the roles required to access
        //each route. The "long" way is to specify each path access here,
        //but it can also be done directly in the controller, using
        //the @Secured (role) annotation on each method
        http.authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**", "/img/**", "/clients", "/locale").permitAll()
                //.antMatchers("/ver/**").hasAnyRole("USER")
                //.antMatchers("/uploads/**").hasAnyRole("USER")
                //.antMatchers("/form/**").hasAnyRole("ADMIN")
                //.antMatchers("/remove/**").hasAnyRole("ADMIN")
                //.antMatchers("/invoice/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successHandler)
                .loginPage("/login").permitAll()    //The .loginPage (path) method serves
                .and()                                            //to tell Spring which path to use
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403");
    }
}
