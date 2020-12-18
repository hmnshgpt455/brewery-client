package com.brewery.client.demo.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomizer implements IRestTemplateCustomizer {

    private final Integer maxTotalConnections;
    private final Integer defaultMaxPerRoute;
    private final Integer connectionRequestTimeout;
    private final Integer connectionSocketTimeout;

    public BlockingRestTemplateCustomizer(@Value("${clients.blocking.connectionManager.maxTotal}") Integer maxTotalConnections,
                                          @Value("${clients.blocking.connectionManager.defaultMaxPerRoute}") Integer defaultMaxPerRoute,
                                          @Value("${clients.blocking.requestConfig.requestTimeOut}") Integer connectionRequestTimeout,
                                          @Value("${clients.blocking.requestConfig.socketTimeOut}") Integer connectionSocketTimeout) {

        this.maxTotalConnections = maxTotalConnections;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.connectionSocketTimeout = connectionSocketTimeout;
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotalConnections);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(connectionSocketTimeout)
                .build();

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
}
