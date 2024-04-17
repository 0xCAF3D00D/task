package org.example.task.client.interceptor

import feign.RequestInterceptor
import feign.RequestTemplate
import org.example.task.config.properties.AppProperties

class UpGateKeyInterceptor(
    private val properties: AppProperties
) : RequestInterceptor {

    override fun apply(template: RequestTemplate) {
        template.header(X_API_KEY)
        template.header(X_API_KEY, properties.apiKey)
    }

    companion object {
        private const val X_API_KEY = "X-Api-Key"
    }

}