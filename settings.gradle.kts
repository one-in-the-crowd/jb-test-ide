pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("jvm") version "1.8.0"
        kotlin("multiplatform") version "1.8.0"
    }
}

rootProject.name = "parser"
include("grammar", "app", "data", "ui")