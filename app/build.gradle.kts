plugins {
    application
    kotlin("jvm") version "1.8.22"
}

repositories {
    mavenCentral()
}

application {
    applicationName = "parser"
    mainClass.set("com.jetbrains.drob.parser.Parser")
}

dependencies {
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
        attributes["Main-Class"] = "com.jetbrains.drob.parser.Parser"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
