package com.nhnacademy.bookstore.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.elasticsearch.ReactiveElasticsearchClientAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;

import java.time.Duration;

@Configuration
public class ElasticSearchConfig extends ReactiveElasticsearchConfiguration {
    @Value("${spring.data.elasticsearch.url}")
    String url;


    // properties 값은 항상 변경될수있음 : bean 생성 따로 -> Value 값 생성해서

    @Override
    public ClientConfiguration clientConfiguration(){
        return ClientConfiguration.builder()
                .connectedTo(url)
                .usingSsl()
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))
                //.withBasicAuth(username, password)
                .build();
    }
}
