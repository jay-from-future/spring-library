package ru.otus.springlibrary.configuration;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import lombok.Data;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Data
@Configuration
public class ApplicationConfiguration {

    public static final String CHANGELOGS_PACKAGE = "ru.otus.springlibrary.changelogs";

    private String database;

    @Bean
    public Mongock mongock(MongoProperties props, MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, props.getDatabase(), CHANGELOGS_PACKAGE).setLockQuickConfig().build();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:error_messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
