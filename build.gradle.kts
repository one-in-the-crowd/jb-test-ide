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
    val path = providers.gradleProperty("parser.generated.path").get()
    delete("app/src/main/java/"+path)
}
