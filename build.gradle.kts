import java.io.ByteArrayOutputStream

tasks.register("build") {
    dependsOn("compileJava")
}

tasks.register("compileJava") {
    dependsOn("generateJava")
    doLast {
        val out = ByteArrayOutputStream()

        exec {
            workingDir(buildDir)

            commandLine("javac", "Adder.java")

            standardOutput = out
        }
        println(out)
    }
}

tasks.register("generateJava") {
    dependsOn("copyGrammar")

    doFirst {
        exec {
            workingDir(buildDir)
            commandLine("javaCC", "adder.jj")
        }
    }
    doLast {
        delete("$buildDir/adder.jj")
    }
}

tasks.register<Copy>("copyGrammar") {
    mkdir("build")
    from("src/adder.jj")
    into("build")
}

tasks.register<Delete>("clean") {
    delete("build", "target")
}