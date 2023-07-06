import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.3.0"
}

group = "com.github.oitc.parser"
version = "1.0-SNAPSHOT"

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)

                implementation(project(":app"))
            }

        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "com.github.oitc.parser.ui.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ui"
            packageVersion = "1.0.0"
        }
    }
}
