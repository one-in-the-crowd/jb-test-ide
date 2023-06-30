# Parser generation project
Generates parser executable that is able to parse given input.

## Points of interest
-  `grammar/parser.jj` contains javaCC grammar specification; 
-  `parser.jar` is a parser executable created by `app`.

## Execution example
1. Build the project with `.gradlew build`;
2. Navigate to `app/build/libs`;
3. Create a file with parser input;
4. Execute parsing (e.g. `java -jar parser.jar < input.txt`).