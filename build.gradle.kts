import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    id("com.diffplug.spotless") version "6.18.0"
}

spotless {
  kotlin {
    ktlint("0.48.2")
  }
}

group = "com.tom.samples"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

dependencies {
    testImplementation("junit", "junit", "4.12")
    testImplementation("org.amshove.kluent:kluent:1.15")
}

