plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.spotless)
}

group = "com.tom.samples"
version = "1.0-SNAPSHOT"

spotless {
    kotlin {
        ktlint(libs.versions.ktlint.get())
            .editorConfigOverride(
                mapOf(
                    "ktlint_standard_property-naming" to "disabled",
                ),
            )
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

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    implementation("org.testng:testng:7.1.0")
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(libs.kotest.core)
}

