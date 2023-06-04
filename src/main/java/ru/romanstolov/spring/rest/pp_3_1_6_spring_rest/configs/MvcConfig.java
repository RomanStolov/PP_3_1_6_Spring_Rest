package ru.romanstolov.spring.rest.pp_3_1_6_spring_rest.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(value = "ru.romanstolov.spring.rest.pp_3_1_6_spring_rest")
public class MvcConfig {

    /**
     * Для совершения http-запроса (Http-реквеста) из rest-клиента используется вспомогательный класс,
     * который предоставляется Spring`ом под названием RestTemplate.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
