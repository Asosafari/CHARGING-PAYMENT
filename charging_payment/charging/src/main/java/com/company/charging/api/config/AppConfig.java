package com.company.charging.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * Author: ASOU SAFARI
 * Date:9/2/24
 * Time:1:02 PM
 */
@Configuration
public class AppConfig {

    String rootUrl= "http://localhost:9090";

    @Bean
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer){
        RestTemplateBuilder builder = configurer.configure(new RestTemplateBuilder());

        assert rootUrl != null;

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(rootUrl);
        return builder.uriTemplateHandler(uriBuilderFactory);
    }
}
