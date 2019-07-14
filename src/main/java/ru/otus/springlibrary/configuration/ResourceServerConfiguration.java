package ru.otus.springlibrary.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${ru.otus.springlibrary.configuration.checkTokenEndpointURL}")
    private String checkTokenEndpointURL;

    @Value("${ru.otus.springlibrary.configuration.resourceId}")
    private String resourceId;

    @Value("${ru.otus.springlibrary.configuration.clientId}")
    private String clientId;

    @Value("${ru.otus.springlibrary.configuration.clientSecret}")
    private String clientSecret;

    @Bean
    public RemoteTokenServices remoteTokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl(checkTokenEndpointURL);
        tokenServices.setClientId(clientId);
        tokenServices.setClientSecret(clientSecret);
        return tokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET)
                .access("hasRole('USER')")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST)
                .access("hasRole('USER')")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE)
                .access("hasRole('ADMIN')");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(resourceId);
    }

}