package com.okta.examples.bookreport;

import com.okta.examples.bookreport.model.Book;
import com.okta.examples.bookreport.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoDefaultConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@SpringBootApplication
public class BookreportApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookreportApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookRepository repository) {
        return (args) -> {
            //repository.save(new Book("Dan Suarez", "Daemon", "http://amzn.to/2AkqWkA", "micah@afitnerd.com"));
            //repository.save(new Book("Dan Suarez", "Freedom™", "http://amzn.to/2AkqWkA", "micah@afitnerd.com"));
            //repository.save(new Book("Neil Gaiman", "American Gods", "http://amzn.to/2AkqWkA", "micah@afitnerd.com"));
        };
    }

    @EnableGlobalMethodSecurity(prePostEnabled = true)
    protected static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
        @Override
        protected MethodSecurityExpressionHandler createExpressionHandler() {
            return new OAuth2MethodSecurityExpressionHandler();
        }
    }

    @Configuration
    @EnableOAuth2Sso
    static class ExampleSecurityConfigurerAdapter extends OAuth2SsoDefaultConfiguration {

        public ExampleSecurityConfigurerAdapter(ApplicationContext applicationContext, OAuth2SsoProperties sso) {
            super(applicationContext, sso);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/", "/images/**").permitAll();
            super.configure(http);
            // after calling super, you can change the logout success url
            http.logout().logoutSuccessUrl("/post_logout").permitAll();
        }
    }
}