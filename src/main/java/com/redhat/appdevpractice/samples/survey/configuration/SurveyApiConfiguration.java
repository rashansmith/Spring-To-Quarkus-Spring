package com.redhat.appdevpractice.samples.survey.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SurveyApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components())
                .info(new Info().title("Survey Group API").description("An API for managing survey groups.")
                        .contact(new Contact().email("na-services-appdev-practice@redhat.com")));
    }
    
}