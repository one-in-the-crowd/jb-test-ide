plugins {
    application
    kotlin("jvm") version "1.8.22"
}

repositories {
    mavenCentral()
}

application {
    applicationName = "parser"
    mainClass.set("com.github.oitc.parser.Parser")
}

dependencies {
    implementation(project(":data"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileJava {
    dependsOn(":grammar:generateJava")
}

tasks.jar {
    archiveFileName.set("parser.jar")
    manifest {
        attributes["Main-Class"] = "com.github.oitc.parser.Parser"
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // Add data module classes
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
