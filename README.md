# Parser generation project
Generates parser executable that is able to parse given input.

## Modules
- `grammar` - contains original grammar for parser generation;
- `app` - module generates parser based on grammar from `grammar`;
- `ui` - contains user interface that allows to interact with parser.

## Parser execution example
1. Build the project with `.gradlew build`;
2. Navigate to `app/build/libs`;
3. Create a file with parser input;
4. Execute parsing (e.g. `java -jar parser.jar < input.txt`).

## Examples
Parser usage examples located at `app/examples`.