package ru.otus.springlibrary;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@EnableAutoConfiguration
@ComponentScan(basePackages = {"ru.otus.springlibrary.service", "ru.otus.springlibrary.domain",
        "ru.otus.springlibrary.repository"})
public class TestApplicationConfiguration {
}