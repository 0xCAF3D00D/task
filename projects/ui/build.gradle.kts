import com.github.gradle.node.npm.task.NpmTask

plugins {
    id("com.github.node-gradle.node") version "7.0.2"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

tasks.register<NpmTask>("npmBuild") {
    args = listOf("run", "build")
}
