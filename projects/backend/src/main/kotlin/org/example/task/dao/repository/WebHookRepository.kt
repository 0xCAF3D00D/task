package org.example.task.dao.repository

import org.example.task.dao.entity.WebHook
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface WebHookRepository : JpaRepository<WebHook, UUID>