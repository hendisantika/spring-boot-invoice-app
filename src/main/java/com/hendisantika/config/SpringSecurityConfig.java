package com.hendisantika.config;

import com.hendisantika.auth.handler.LoginSuccessHandler;
import com.hendisantika.service.impl.JPAUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    private final LoginSuccessHandler successHandler;

    private final JPAUserDetailsServiceImpl userDetailsService;

    public SpringSecurityConfig(LoginSuccessHandler successHandler, JPAUserDetailsServiceImpl userDetailsService) {
        this.successHandler = successHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //you can either disable this or
                //put <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                //inside the login form
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/h2-console/**", "/css/**",
                                "/js/**", "/img/**", "/clients", "/locale").permitAll()
                        /***
                         * Here we specify the roles required to access
                         * each route. The "long" way is to specify each path access here,
                         * but it can also be done directly in the controller, using
                         * the @Secured (role) annotation on each method
                         */
//                        .requestMatchers("/ver/**").hasAnyRole("USER")
//                        .requestMatchers("/uploads/**").hasAnyRole("USER")
//                        .requestMatchers("/form/**").hasAnyRole("ADMIN")
//                        .requestMatchers("/remove/**").hasAnyRole("ADMIN")
//                        .requestMatchers("/invoice/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") //enable this to go to your own custom login page
                        .loginProcessingUrl("/login") //enable this to use login page provided by spring security
                        .defaultSuccessUrl("/", true)
                        .successHandler(successHandler)
                        .failureUrl("/error_403")
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                );

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }
}
