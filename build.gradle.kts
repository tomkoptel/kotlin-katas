import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.spotless)
}

group = "com.tom.samples"
version = "1.0-SNAPSHOT"

spotless {
    kotlin {
        ktlint(libs.versions.ktlint.get())
    }
}

java {
    val javaVersion = JavaVersion.toVersion(libs.versions.jvm.get())
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = libs.versions.jvm.get()
}

dependencies {
    implementation("org.testng:testng:7.1.0")
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.core)
}

