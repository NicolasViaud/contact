package com.viaud.contacts;

import java.util.List;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author Viaud
 */
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    /**
     * Active kaczmarzyk framework for resolve spring specification in controller
     * https://github.com/tkaczmarzyk/specification-arg-resolver
     * @param argumentResolvers 
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
       argumentResolvers.add(new SpecificationArgumentResolver());
    }
    
    
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
}
