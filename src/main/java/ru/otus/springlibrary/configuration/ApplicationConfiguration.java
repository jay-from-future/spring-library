package ru.otus.springlibrary.configuration;

import com.github.mongobee.Mongobee;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "library")
public class ApplicationConfiguration {

    @Bean
    public Mongobee mongobee() {
        // todo define host and port from yml file
        Mongobee runner = new Mongobee("mongodb://localhost:27017/library");
        runner.setChangeLogsScanPackage("ru.otus.springlibrary.changelogs");
        return runner;
    }
}
