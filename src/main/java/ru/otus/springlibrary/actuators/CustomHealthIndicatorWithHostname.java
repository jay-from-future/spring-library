package ru.otus.springlibrary.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class CustomHealthIndicatorWithHostname implements HealthIndicator {

    @Override
    public Health health() {
        String hostname = "unknown";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
        } catch (UnknownHostException e) {
            System.err.println("Cannot resolve hostname: " + e.getMessage());
        }
        return Health.down().withDetail("hostname", hostname).build();
    }
}
