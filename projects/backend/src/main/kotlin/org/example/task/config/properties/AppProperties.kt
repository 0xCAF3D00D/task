package org.example.task.config.properties

import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties

@ConfigurationProperties("app")
data class AppProperties(
    @field:NotEmpty val apiKey: String = "key",
)