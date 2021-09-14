package com.hendisantika.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/09/21
 * Time: 05.28
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final Logger log = LoggerFactory.getLogger(getClass());

    //Option to be able to load the images from the directory
    //It can also be done in the controller
	/*@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		String resourcesPath = Paths.get("uploads").toAbsolutePath().toUri().toString();
		registry.addResourceHandler("/uploads/**")
			.addResourceLocations(resourcesPath);
		log.info("resourcesPath: " + resourcesPath);
	}*/

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error_403")
                .setViewName("error_403");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        //We use the BCrypt algorithm, which is currently the most powerful
        //since the result of the encryption is different for the same
        //password
        return new BCryptPasswordEncoder();
    }

    //This method takes care of saving, in this case in the session (although
    //can be saved in cookies, etc) the "locale".
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("es", "ID"));
        return localeResolver;
    }

    //This method is responsible for intercepting, hence its name, every time
    //change the language
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");    //Name that we want for the language parameter in the url
        return interceptor;
    }

}
