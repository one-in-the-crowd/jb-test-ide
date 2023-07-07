val packageName = providers.gradleProperty("parser.generated.package").get()

plugins {
    application
    kotlin("jvm")
}

application {
    applicationName = "parser"
    mainClass.set(packageName+".Parser")
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
        attributes["Main-Class"] = packageName+".Parser"
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
