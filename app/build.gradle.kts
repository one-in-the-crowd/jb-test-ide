plugins {
    application
}

repositories {
    mavenCentral()
}

application {
    applicationName = "parser"
    mainClass.set("com.jetbrains.drob.parser.Adder")
}

tasks.compileJava {
    dependsOn(":grammar:generateJava")
}

tasks.jar {
    archiveFileName.set("parser.jar")
    manifest {
        attributes["Main-Class"] = "com.jetbrains.drob.parser.Adder"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
