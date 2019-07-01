package ru.otus.springlibrary.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Bean
    public RemoteTokenServices remoteTokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        // todo these values shouldn't be hardcoded
        tokenServices.setCheckTokenEndpointUrl("https://spring-library-auth.herokuapp.com/oauth/check_token");
        tokenServices.setClientId("spring-library-jwt-client");
        tokenServices.setClientSecret("admin1234");
        return tokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers("/**")
                .access("#oauth2.hasScope('read')");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("spring-library-oauth2-server");
    }

}