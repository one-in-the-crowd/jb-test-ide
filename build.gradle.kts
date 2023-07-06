plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

tasks.register<Delete>("clean") {
    delete("app/src/main/java")
}
