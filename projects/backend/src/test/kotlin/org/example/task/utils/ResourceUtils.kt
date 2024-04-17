package org.example.task.utils

import org.springframework.core.io.ClassPathResource
import kotlin.text.Charsets.UTF_8

object ResourceUtils {

    fun getContentAsString(resource: String): String {
        return ClassPathResource(resource).getContentAsString(UTF_8)
    }

}