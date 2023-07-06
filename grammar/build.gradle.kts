val parserSourceCodeDir = "../app/src/main/java/"+providers.gradleProperty("parser.generated.path").get()

tasks.register("generateJava") {
    dependsOn("copyGrammar")

    doFirst {
        exec {
            workingDir(parserSourceCodeDir)
            commandLine("javaCC", "parser.jj")
        }
    }
}

tasks.register<Copy>("copyGrammar") {
    mkdir(parserSourceCodeDir)
    from("parser.jj")
    into(parserSourceCodeDir)
}