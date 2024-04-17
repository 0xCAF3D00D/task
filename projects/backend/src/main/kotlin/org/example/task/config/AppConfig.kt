package org.example.task.config

import feign.RequestInterceptor
import org.example.task.client.interceptor.UpGateKeyInterceptor
import org.example.task.config.properties.AppProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(AppProperties::class)
class AppConfig {

    @Bean
    fun apiKeyRequestInterceptor(properties: AppProperties): RequestInterceptor {
        return UpGateKeyInterceptor(properties)
    }

}