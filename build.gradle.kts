/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM
    id("org.jetbrains.kotlin.jvm").version("1.3.10")

    // Apply the application to add support for building a CLI application
    application
}

repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
    maven (url="https://dl.bintray.com/pgutkowski/Maven")
}

dependencies {
    // Use the Kotlin JDK 8 standard library
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.exposed:exposed:0.11.2")
    implementation("org.http4k:http4k-core:3.102.0")
    implementation("org.postgresql:postgresql:42.2.2")
    implementation("com.github.pgutkowski:kgraphql:0.3.0")
    implementation("org.http4k:http4k-server-jetty:3.102.0")
    // Use the Kotlin test library
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    // Define the main class for the application
    mainClassName = "com.github.jmlb23.movielense.MainKt"
}