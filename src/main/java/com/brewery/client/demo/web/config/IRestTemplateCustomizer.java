package com.brewery.client.demo.web.config;

import org.springframework.web.client.RestTemplate;

/**
 * Callback interface that can be used to customize a {@link RestTemplate}
 *
 * @see org.springframework.boot.web.client.RestTemplateBuilder
 */

@FunctionalInterface
public interface IRestTemplateCustomizer {

    /**
     * Callback to customize a {@link RestTemplate} instance
     * @param restTemplate instance of {@link RestTemplate} that will be injected by spring
     */
    void customize(RestTemplate restTemplate);
}
