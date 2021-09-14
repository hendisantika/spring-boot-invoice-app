package com.hendisantika.config;

import com.hendisantika.auth.handler.LoginSuccessHandler;
import com.hendisantika.service.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder build) throws Exception {
        //This code allows to implement users "in memory"
        //UserBuilder users = User.withDefaultPasswordEncoder(); //Spring Boot 1.5.10
		/*PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder(); //Spring Boot 2
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);	//Spring Boot 2
		build.inMemoryAuthentication()
		.withUser(
				users.username("admin")
				.password("admin")
				.roles("ADMIN", "USER"))
		.withUser(
				users.username("cristian")
				.password("cristian")
				.roles("USER"));*/

        //This code allows to implement users with database through JDBC
        //You have to indicate the queries to execute to login and obtain the user roles
		/*build.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("SELECT USERNAME, PASSWORD, ENABLED FROM USERS WHERE USERNAME=?")
		.authoritiesByUsernameQuery(
				"SELECT U.USERNAME, A.AUTHORITY "
						+ "FROM AUTHORITIES A INNER JOIN USERS U "
						+ "ON (A.USER_ID = U.ID) "
						+ "WHERE U.USERNAME=?");*/

        //This code allows to implement users with database through JPA.
        build.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
