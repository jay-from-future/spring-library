package ru.otus.springlibrary.configuration;

import com.github.mongobee.Mongobee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:application.yml")
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class ApplicationConfiguration {

    private String host;

    private String port;

    @Bean
    public Mongobee mongobee() {
        Mongobee runner = new Mongobee("mongodb://" + host + ":" + port + "/library");
        runner.setChangeLogsScanPackage("ru.otus.springlibrary.changelogs");
        return runner;
    }

    @Value("${host}")
    public void setHost(String host) {
        this.host = host;
    }

    @Value("${port}")
    public void setPort(String port) {
        this.port = port;
    }
}
